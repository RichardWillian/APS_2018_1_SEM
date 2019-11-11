package ecochat.aplicacoes.servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import ecochat.entidades.DadoCompartilhado;
import ecochat.utilitarios.ConstantesGerais;

public class ServidorChat {

	private static Socket socketServidorCentral;
	private static ServerSocket socketServidorChat;
	private static List<Socket> socketsConectados;
	private static List<String> ipsSocketsConectados;

	public static void main(String[] args) throws UnknownHostException, IOException {

		iniciarServidor();
		conectarServidorCentral();
	}

	public static void iniciarServidor() {

		new Thread() {
			public void run() {

				try {

					System.out.println("Servidor Chat Conectado");

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
							System.out.println("(Servidor Chat) Ip conectou: " + ipSocket);

							if (!ipsSocketsConectados.contains(ipSocket)) {
								socketsConectados.add(socket);
								ipsSocketsConectados.add(ipSocket);
							}

							lerMensagemDoCliente(new ObjectInputStream(socket.getInputStream()));
						}
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

						DadoCompartilhado dadoCompartilhado = (DadoCompartilhado) fluxoEntradaDados.readObject();

						for (Socket socketConectado : socketsConectados) {

							String ipSocketConectado = socketConectado.getInetAddress().getHostAddress();
							if (ipSocketConectado.equals(dadoCompartilhado.getDestinatario())) {

								if (!socketConectado.isClosed()) {
									
									ServidorChat.notificarUsuario(dadoCompartilhado);

									ServidorChat.enviarMensagem(socketConectado, dadoCompartilhado);
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

	public static void enviarMensagem(Socket socketQueReceberaMensagem, DadoCompartilhado dadoCompartilhado) {

		try {
			ObjectOutputStream fluxoSaidaDados = new ObjectOutputStream(socketQueReceberaMensagem.getOutputStream());
			fluxoSaidaDados.writeObject(dadoCompartilhado);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void notificarUsuario(final DadoCompartilhado dadoCompartilhado) {

		ObjectOutputStream fluxoSaidaDados;
		try {

			fluxoSaidaDados = new ObjectOutputStream(socketServidorCentral.getOutputStream());

			fluxoSaidaDados.writeObject(dadoCompartilhado);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void conectarServidorCentral() throws UnknownHostException, IOException {

		new Thread() {

			public void run() {
				boolean conectouServidorCentral = false;

				while (!conectouServidorCentral) {
					try {
						InetAddress inetAddressServidorCentral = InetAddress
								.getByName(ConstantesGerais.IP_SERVIDOR_CENTRAL);
						InetAddress inetAddressAplicacaoCorrente = InetAddress
								.getByName(ConstantesGerais.IP_SERVIDOR_CHAT);

						socketServidorCentral = new Socket(inetAddressServidorCentral,
								ConstantesGerais.PORTA_SERVIDOR_CENTRAL, inetAddressAplicacaoCorrente, 0);
						conectouServidorCentral = true;
					} catch (Exception e) {
						conectouServidorCentral = false;
					}
				}
			}
		}.start();
	}
}
