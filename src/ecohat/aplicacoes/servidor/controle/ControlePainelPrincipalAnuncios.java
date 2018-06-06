package ecohat.aplicacoes.servidor.controle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import ecochat.entidades.DadoCompartilhadoServidor;
import ecochat.interfaces.telas.UIJanelaPrincipal;
import ecochat.utilitarios.ConstantesGerais;
import ecochat.utilitarios.Utilitaria;

public class ControlePainelPrincipalAnuncios {

	private static Socket socketServidorCentral;
	private static Socket socketServidorChat;
	private static ObjectOutputStream fluxoSaidaDados;
	private static ControlePainelPrincipalAnuncios instancia;
	private String ipMaquina;

	private ControlePainelPrincipalAnuncios() {
		try {
			ipMaquina = Utilitaria.criarIpAleatorio();
			UIJanelaPrincipal.getInstance(ipMaquina);
			conectarServidor();
			iniciarLeituraAtualizacoesSistema();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void conectarServidor() throws UnknownHostException, IOException, InterruptedException {

		socketServidorCentral = new Socket(InetAddress.getByName(ConstantesGerais.IP_SERVIDOR_CENTRAL),
				ConstantesGerais.PORTA_SERVIDOR_CENTRAL, InetAddress.getByName(ipMaquina), 0);

		Thread.sleep(1000);

		socketServidorChat = new Socket(InetAddress.getByName(ConstantesGerais.IP_SERVIDOR_CHAT),
				ConstantesGerais.PORTA_SERVIDOR_CHAT, InetAddress.getByName(ipMaquina), 0);

		fluxoSaidaDados = new ObjectOutputStream(socketServidorChat.getOutputStream());
		fluxoSaidaDados.flush();
	}

	private void iniciarLeituraAtualizacoesSistema() {

		new Thread() {
			public void run() {
				while (true) {
					try {

						ObjectInputStream fluxoEntradaDados = new ObjectInputStream(
								socketServidorCentral.getInputStream());
						DadoCompartilhadoServidor dadoCompartilhadoServidor = (DadoCompartilhadoServidor) fluxoEntradaDados
								.readObject();

						String ipUsuarioConectou = dadoCompartilhadoServidor.getIpUsuarioConectou();

						if (ipUsuarioConectou != null)
							UIJanelaPrincipal.getInstance().adicionarUsuariosOnline(ipUsuarioConectou);

						// TODO FAZER ENVIO DE ANÚNCIOS
						if(dadoCompartilhadoServidor.getAnuncio() != null) {
							UIJanelaPrincipal.getInstance().adicionaPainel(dadoCompartilhadoServidor.getAnuncio());
						}
						
						if (dadoCompartilhadoServidor.getDadoCompartilhado() != null)
							UIJanelaPrincipal.getInstance()
									.notificarUsuario(dadoCompartilhadoServidor
								    .getDadoCompartilhado().getRemetente());

					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (Exception ex) {
						ex.printStackTrace();
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

	public static ControlePainelPrincipalAnuncios getInstance() {

		if (instancia == null)
			return instancia = new ControlePainelPrincipalAnuncios();

		return instancia;
	}
}
