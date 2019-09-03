package ecochat.aplicacoes.servidor.controle;

import java.net.UnknownHostException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.io.IOException;
import java.net.Socket;

import ecochat.entidades.DadoCompartilhadoServidor;
import ecochat.interfaces.telas.UIJanelaPrincipal;
import ecochat.utilitarios.ConstantesGerais;
import ecochat.utilitarios.Utilitaria;

public class ControlePainelPrincipalAnuncios {

	private static Socket socketServidorCentral;
	private static Socket socketServidorChat;
	private static ObjectOutputStream fluxoSaidaDados;
	private static ControlePainelPrincipalAnuncios instancia;
	private static String ipMaquina;
	private static ArrayList<String> socketsConectados;

	private ControlePainelPrincipalAnuncios() throws UnknownHostException, IOException, InterruptedException {

		socketsConectados = new ArrayList<String>();
		ipMaquina = Utilitaria.criarIpAleatorio();
		conectarServidores();
		UIJanelaPrincipal.getInstance(ipMaquina);
		iniciarLeituraAtualizacoesSistema();
	}

	private static void conectarServidores() throws UnknownHostException, IOException, InterruptedException {

		conectarServidorCentral();

		Thread.sleep(1000);

		socketServidorChat = new Socket(InetAddress.getByName(ConstantesGerais.IP_SERVIDOR_CHAT),
				ConstantesGerais.PORTA_SERVIDOR_CHAT, InetAddress.getByName(ipMaquina), 0);

		fluxoSaidaDados = new ObjectOutputStream(socketServidorChat.getOutputStream());
		fluxoSaidaDados.flush();
	}

	public static void conectarServidorCentral() throws UnknownHostException, IOException {

		InetAddress inetAddressServidorCentral = InetAddress.getByName(ConstantesGerais.IP_SERVIDOR_CENTRAL);
		InetAddress inetAddressAplicacaoCorrente = InetAddress.getByName(ipMaquina);

		socketServidorCentral = new Socket(inetAddressServidorCentral, ConstantesGerais.PORTA_SERVIDOR_CENTRAL,
				inetAddressAplicacaoCorrente, 0);
	}

	public static void iniciarLeituraAtualizacoesSistema() {

		new Thread() {

			public void run() {

				while (true) {

					try {
						while (true) {

							ObjectInputStream fluxoEntradaDados = new ObjectInputStream(
									socketServidorCentral.getInputStream());
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

						while (true) {
							try {
								conectarServidorCentral();
								System.out.println("O usuário se conectou novamente !");
								Thread.sleep(3000);
								break;
							} catch (IOException | InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}.start();
	}

	public String getIpAplicacao() {
		return ipMaquina;
	}

	public Socket getSocket() {
		return socketServidorChat;
	}

	public ObjectOutputStream getFluxoSaidaDados() {
		return fluxoSaidaDados;
	}

	public static ControlePainelPrincipalAnuncios getInstance()
			throws UnknownHostException, IOException, InterruptedException {

		if (instancia == null)
			return instancia = new ControlePainelPrincipalAnuncios();

		return instancia;
	}
}
