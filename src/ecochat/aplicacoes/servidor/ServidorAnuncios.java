package ecochat.aplicacoes.servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import ecochat.entidades.DadoAnuncio;
import ecochat.entidades.DadoCompartilhadoServidor;
import ecochat.utilitarios.ConstantesGerais;

public class ServidorAnuncios {

	private static ServerSocket socketServidorAnuncio;
	private static List<Socket> socketsConectados;
	private static List<String> ipsSocketsConectados;
	private static List<DadoAnuncio> listaAnuncios;

	public static void main(String[] args) throws UnknownHostException, IOException {

		iniciarServidor();
	}

	public static void iniciarServidor() {

		new Thread() {
			public void run() {

				try {

					System.out.println("Servidor Anúncio Conectado");

					if (socketServidorAnuncio == null || socketServidorAnuncio.isClosed())
						socketServidorAnuncio = new ServerSocket(ConstantesGerais.PORTA_SERVIDOR_CHAT);

					if (socketsConectados == null || !(socketsConectados.size() > 0))
						socketsConectados = new ArrayList<Socket>();

					if (ipsSocketsConectados == null || !(ipsSocketsConectados.size() > 0))
						ipsSocketsConectados = new ArrayList<String>();

					while (true) {

						if (!socketServidorAnuncio.isClosed()) {
							Socket socket = socketServidorAnuncio.accept();
							String ipSocket = socket.getInetAddress().getHostAddress();
							System.out.println("(Servidor Anúncio) Ip conectou: " + ipSocket);

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

						DadoCompartilhadoServidor dadoCompartilhado = (DadoCompartilhadoServidor) fluxoEntradaDados
								.readObject();

						listaAnuncios.add(dadoCompartilhado.getAnuncio().get(0));

						List<Socket> socketsConectadosCopia = new ArrayList<Socket>(socketsConectados);
						for (Socket socketConectado : socketsConectadosCopia) {
							ObjectOutputStream fluxoSaidaDados = new ObjectOutputStream(
									socketConectado.getOutputStream());
							fluxoSaidaDados.writeObject(dadoCompartilhado);
						}
					}
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}
			}
		}.start();
	}
}
