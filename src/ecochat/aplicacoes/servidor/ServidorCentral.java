package ecochat.aplicacoes.servidor;

import java.awt.print.Printable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import ecochat.entidades.DadoAnuncio;
import ecochat.entidades.DadoCompartilhado;
import ecochat.entidades.DadoCompartilhadoServidor;
import ecochat.interfaces.telas.UIJanelaServidorCentral;
import ecochat.utilitarios.ConstantesGerais;

public class ServidorCentral {

	private static ServerSocket socketServidorCentral;
	private static List<String> ipsSocketsConectados;
	private static List<DadoAnuncio> listaAnuncios;
	private static List<Socket> socketsConectados;
	private static Socket socketChat;
	private static Socket socketAnuncios;
	private static boolean servidorCentralLigado;

	public static void main(String[] args) {

		try {
			UIJanelaServidorCentral.getInstance();
			notificarUsuario();
		} catch (Exception e) {
			System.err.println("Ops! " + e.getMessage() + "\n");
		}
	}

	public static void iniciarServidor() {

		try {

			if (socketServidorCentral == null || socketServidorCentral.isClosed()) {

				socketServidorCentral = new ServerSocket(ConstantesGerais.PORTA_SERVIDOR_CENTRAL);
				servidorCentralLigado = true;
			}

			if (socketsConectados == null || !(socketsConectados.size() > 0))
				socketsConectados = new ArrayList<Socket>();

			if (ipsSocketsConectados == null || !(ipsSocketsConectados.size() > 0))
				ipsSocketsConectados = new ArrayList<String>();

			if (listaAnuncios == null || !(listaAnuncios.size() > 0))
				listaAnuncios = new ArrayList<DadoAnuncio>();

			UIJanelaServidorCentral.getInstance().mostrarMensagem("        ---===== Servidor Conectado =====---");

			while (true) {
				
				Socket socket = socketServidorCentral.accept();
				String ipConectado = socket.getInetAddress().getHostAddress();

				if (ipConectado.equals(ConstantesGerais.IP_SERVIDOR_CHAT)) {

					socketChat = socket;

				} else if (servidorCentralLigado) {

					UIJanelaServidorCentral.getInstance().mostrarConectados(ipConectado);
					socketsConectados.add(socket);

					if (!ipsSocketsConectados.contains(ipConectado)) {

						ipsSocketsConectados.add(ipConectado);
					}
				}
			}
		} catch (IOException ioE) {
			ioE.printStackTrace();
		}
	}

	public static void desligarServidor() {
		try {

			servidorCentralLigado = false;

			for (Socket socketConectado : socketsConectados) {
				socketConectado.close();
			}

			UIJanelaServidorCentral.getInstance().mostrarMensagem("     ---===== Servidor Desconectado =====---");
			socketServidorCentral.close();
			socketsConectados = new ArrayList<Socket>();
			Thread.currentThread().interrupt();

		} catch (IOException ioE) {
			System.err.println("Falha ao desligar o servidor\n\n" + ioE.getMessage());
		}
	}

	public static void notificarUsuario() {
		new Thread() {
			public void run() {
				
				while (true) {
					try {

						if (socketChat != null && !socketChat.isClosed()) {

							ObjectInputStream fluxoEntradaDados = new ObjectInputStream(socketChat.getInputStream());
							DadoCompartilhado dadoCompartilhado = (DadoCompartilhado) fluxoEntradaDados.readObject();

							for (Socket socketConectado : socketsConectados) {

								String ipSocketConectado = socketConectado.getInetAddress().getHostAddress();

								if (ipSocketConectado.equals(dadoCompartilhado.getDestinatario())) {
									ObjectOutputStream fluxoSaidaDados;

									fluxoSaidaDados = new ObjectOutputStream(socketConectado.getOutputStream());

									DadoCompartilhadoServidor dadoCompartilhadoServidor = new DadoCompartilhadoServidor();
									dadoCompartilhadoServidor.setDadoCompartilhado(dadoCompartilhado);

									fluxoSaidaDados.writeObject(dadoCompartilhadoServidor);
								}
							}
						} else {
							System.out.println("Esperando o 'Servidor Chat' se conectar");
							try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					} catch (IOException | ClassNotFoundException e) {
						try {
							socketChat.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		}.start();
	}
}
