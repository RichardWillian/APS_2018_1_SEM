package ecochat.aplicacoes.servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import ecochat.entidades.DadoCompartilhado;
import ecochat.interfaces.telas.UIJanelaServidorCentral;
import ecochat.utilitarios.ConstantesGerais;

public class ServidorChat {

	private static ServerSocket socketServidorChat;
	private static List<Socket> socketsConectados;
	private static List<String> ipsSocketsConectados;
	private static ServidorChat instancia;

	public static ServidorChat getInstance() {

		if (instancia == null)
			return instancia = new ServidorChat();

		return instancia;
	}

	public void iniciarServidor() {

		new Thread() {
			public void run() {

				try {

					if (socketServidorChat == null || socketServidorChat.isClosed())
						socketServidorChat = new ServerSocket(ConstantesGerais.PORTA_SERVIDOR_CHAT);

					if (socketsConectados == null || !(socketsConectados.size() > 0))
						socketsConectados = new ArrayList<Socket>();

					if (ipsSocketsConectados == null || !(ipsSocketsConectados.size() > 0))
						ipsSocketsConectados = new ArrayList<String>();

					while (true) {

						if (!socketServidorChat.isClosed()) {
							Socket socket = socketServidorChat.accept();
							String ipSocket = socket.getInetAddress().getHostAddress();

							if (!ipsSocketsConectados.contains(ipSocket)) {
								socketsConectados.add(socket);
								ipsSocketsConectados.add(ipSocket);
							}

							lerMensagemDoCliente(new ObjectInputStream(socket.getInputStream()));
						}
					}
				} catch (IOException e) {

				}
			}
		}.start();
	}

	private static void lerMensagemDoCliente(final ObjectInputStream fluxoEntradaDados) {
		new Thread() {

			public void run() {

				try {
					while (true) {

						Socket socketQueReceberaMensagem = null;

						DadoCompartilhado dadoCompartilhado = (DadoCompartilhado) fluxoEntradaDados.readObject();

						for (Socket socketConectado : socketsConectados) {
							
							String ipSocketConectado = socketConectado.getInetAddress().getHostAddress();
							if (ipSocketConectado.equals(dadoCompartilhado.getDestinatario())) {

								if (!socketConectado.isClosed()) {
									socketQueReceberaMensagem = socketConectado;

									ServidorCentral.getInstance().notificarUsuario(dadoCompartilhado);

									ServidorChat.getInstance().enviarMensagem(socketQueReceberaMensagem,
											dadoCompartilhado);
								}
							}
						}
					}
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}
			}
		}.start();
	}

	public void enviarMensagem(Socket socketQueReceberaMensagem, DadoCompartilhado dadoCompartilhado) {

		try {
			ObjectOutputStream fluxoSaidaDados = new ObjectOutputStream(socketQueReceberaMensagem.getOutputStream());
			fluxoSaidaDados.writeObject(dadoCompartilhado);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void desligarServidor() {
		try {

			socketServidorChat.close();

		} catch (IOException ioE) {
			System.err.println("Falha ao desligar o servidor\n\n" + ioE.getMessage());
		}
	}
}
