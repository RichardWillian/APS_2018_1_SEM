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
import ecochat.interfaces.telas.UIJanelaChat;
import ecochat.interfaces.telas.UIJanelaPrincipal;
import ecochat.utilitarios.ConstantesGerais;

public class ServidorChat {

	private static ServerSocket socketServidorChat;
	private static List<Socket> socketsConectados;
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
					socketServidorChat = new ServerSocket(ConstantesGerais.PORTA_SERVIDOR_CHAT,
							ConstantesGerais.QUANTIDADE_MAXIMA_CONECTADOS,
							InetAddress.getByName(ConstantesGerais.IP_SERVIDOR_CHAT));

					socketsConectados = new ArrayList<Socket>();
					while (true) {
						Socket socket = socketServidorChat.accept();
						socketsConectados.add(socket);
						lerMensagemDoCliente(new ObjectInputStream(socket.getInputStream()));
					}
				} catch (IOException e) {
					e.printStackTrace();
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
							if (socketConectado.getInetAddress().getHostAddress()
									.equals(dadoCompartilhado.getEmailEntrega())) {

								if (!socketConectado.isClosed()) {
									socketQueReceberaMensagem = socketConectado;
									if(!UIJanelaChat.getInstance().isFocusable()){
										UIJanelaPrincipal.getInstance().alertaMensagem(dadoCompartilhado.getEmailEntrega());
										ServidorChat.getInstance().enviarMensagem(socketQueReceberaMensagem,
											dadoCompartilhado);
									}else
										ServidorChat.getInstance().enviarMensagem(socketQueReceberaMensagem,
												dadoCompartilhado);
								}
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
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

}
