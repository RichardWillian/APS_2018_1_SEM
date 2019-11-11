package ecochat.aplicacoes.servidor.controle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import ecochat.entidades.DadoCompartilhadoServidor;
import ecochat.interfaces.telas.UIJanelaPrincipal;
import ecochat.utilitarios.ConstantesGerais;

public class ControlePainelPrincipalAnuncios {

	private static Socket socketServidorCentral;
	private static Socket socketServidorAnuncios;
	private static Socket socketServidorChat;
	private static ObjectOutputStream fluxoSaidaDadosServidorChat;
	private static ObjectOutputStream fluxoSaidaDadosServidorAnuncios;
	private static ControlePainelPrincipalAnuncios instancia;
	private static String ipMaquina;
	private static ArrayList<String> socketsConectados;

	private ControlePainelPrincipalAnuncios() throws UnknownHostException, IOException, InterruptedException {

		socketsConectados = new ArrayList<String>();
		ipMaquina = InetAddress.getLocalHost().getHostAddress();
		conectarServidores();
		UIJanelaPrincipal.getInstance(ipMaquina);
		iniciarLeituraAtualizacoesSistema();
	}

	private static void conectarServidores() throws UnknownHostException, IOException, InterruptedException {

		 conectarServidorCentral();
		 Thread.sleep(1000);
		
		 conectarServidorChat();
		
		 Thread.sleep(1000);
		
		 fluxoSaidaDadosServidorChat = new
		 ObjectOutputStream(socketServidorChat.getOutputStream());
		 fluxoSaidaDadosServidorChat.flush();

		conectarServidorAnuncios();

		Thread.sleep(1000);

		fluxoSaidaDadosServidorAnuncios = new ObjectOutputStream(socketServidorAnuncios.getOutputStream());
		fluxoSaidaDadosServidorAnuncios.flush();
	}

	private static void conectarServidorChat() throws IOException, UnknownHostException {

		InetAddress inetAddressServidorChat = InetAddress.getByName(ConstantesGerais.IP_SERVIDOR_CHAT);
		InetAddress inetAddressAplicacaoCorrente = InetAddress.getByName(ipMaquina);

		socketServidorChat = new Socket(inetAddressServidorChat, ConstantesGerais.PORTA_SERVIDOR_CHAT,
				inetAddressAplicacaoCorrente, 0);
	}

	public static void conectarServidorCentral() throws UnknownHostException, IOException {

		InetAddress inetAddressServidorCentral = InetAddress.getByName(ConstantesGerais.IP_SERVIDOR_CENTRAL);
		InetAddress inetAddressAplicacaoCorrente = InetAddress.getByName(ipMaquina);

		socketServidorCentral = new Socket(inetAddressServidorCentral, ConstantesGerais.PORTA_SERVIDOR_CENTRAL,
				inetAddressAplicacaoCorrente, 0);
	}

	private static void conectarServidorAnuncios() throws UnknownHostException, IOException {

		InetAddress inetAddressServidorAnuncios = InetAddress.getByName(ConstantesGerais.IP_SERVIDOR_ANUNCIOS);
		InetAddress inetAddressAplicacaoCorrente = InetAddress.getByName(ipMaquina);

		socketServidorAnuncios = new Socket(inetAddressServidorAnuncios, ConstantesGerais.PORTA_SERVIDOR_ANUNCIOS,
				inetAddressAplicacaoCorrente, 0);
	}

	public static void iniciarLeituraAtualizacoesSistema() {

		new Thread() {

			public void run() {

				while (true) {

					try {
						while (true) {
							ObjectInputStream fluxoEntradaDados = new ObjectInputStream(
									socketServidorAnuncios.getInputStream());
							DadoCompartilhadoServidor dadoCompartilhadoServidor = (DadoCompartilhadoServidor) fluxoEntradaDados
									.readObject();

							String ipUsuarioConectou = dadoCompartilhadoServidor.getIpUsuarioConectou();

							if (!socketsConectados.contains(ipUsuarioConectou)) {

								if (ipUsuarioConectou != null) {
									UIJanelaPrincipal.getInstance().adicionarUsuariosOnline(ipUsuarioConectou);
									socketsConectados.add(ipUsuarioConectou);
								}

								boolean possuiAnuncios = dadoCompartilhadoServidor.getAnuncio() != null
										&& dadoCompartilhadoServidor.getAnuncio().size() > 0;

								if (possuiAnuncios) {
									UIJanelaPrincipal.getInstance()
											.adicionaPainel(dadoCompartilhadoServidor.getAnuncio());
								}

								if (dadoCompartilhadoServidor.getDadoCompartilhado() != null)
									UIJanelaPrincipal.getInstance().notificarUsuario(
											dadoCompartilhadoServidor.getDadoCompartilhado().getRemetente());
							}
						}
					} catch (Exception ex) {

						System.out.println("Obtivemos um problema com o servidor, por favor aguarde");
						try {

							socketServidorCentral.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						while (true) {
							try {
								conectarServidorCentral();
								System.out.println("O usuário se conectou ao servidor central novamente !");
								Thread.sleep(3000);
								break;
							} catch (IOException | InterruptedException e) {

							}
						}
					}
				}
			}
		}.start();
	}

	public void enviarAnunciosPaineis(DadoCompartilhadoServidor dadoCompartilhadoServidor) {

		try {

			ObjectOutputStream fluxoSaidaAnuncio = getFluxoSaidaDadosServidorAnuncios();
			fluxoSaidaAnuncio.writeObject(dadoCompartilhadoServidor);

			fluxoSaidaAnuncio.flush();

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public String getIpAplicacao() {
		return ipMaquina;
	}

	public boolean servidorCentralIsClosed() {
		return socketServidorCentral.isClosed();
	}

	public Socket getSocketServidorChat() {
		return socketServidorChat;
	}

	public ObjectOutputStream getFluxoSaidaDadosServidorChat() {
		return fluxoSaidaDadosServidorChat;
	}

	public ObjectOutputStream getFluxoSaidaDadosServidorAnuncios() {
		return fluxoSaidaDadosServidorAnuncios;
	}

	public static ControlePainelPrincipalAnuncios getInstance()
			throws UnknownHostException, IOException, InterruptedException {

		if (instancia == null)
			return instancia = new ControlePainelPrincipalAnuncios();

		return instancia;
	}
}
