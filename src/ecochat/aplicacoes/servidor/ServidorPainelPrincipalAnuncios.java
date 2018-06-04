package ecochat.aplicacoes.servidor;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

import ecochat.interfaces.telas.UIJanelaPrincipal;
import ecochat.utilitarios.ConstantesGerais;
import ecochat.utilitarios.Utilitaria;

public class ServidorPainelPrincipalAnuncios {

	private static Socket socketServidorCentral;
	private static Socket socketServidorChat;
	private static ObjectOutputStream fluxoSaidaDados;
	private static ServidorPainelPrincipalAnuncios instancia;

	private ServidorPainelPrincipalAnuncios() {
		try {
			conectarServidorCentral();
			atualizarSocketsConectados();
			UIJanelaPrincipal.getInstance();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void atualizarSocketsConectados() {

		new Thread() {
			public void run() {
				while (true) {
					try {

						ObjectInputStream fluxoEntradaDados = new ObjectInputStream(
								socketServidorCentral.getInputStream());

						try {

							String ipSocketConectado = (String) fluxoEntradaDados.readObject();
							UIJanelaPrincipal.getInstance().adicionarUsuariosOnline(ipSocketConectado);
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	private void conectarServidorCentral() throws UnknownHostException, IOException, InterruptedException {
		
		String ipMaquina = Utilitaria.criarIpAleatorio();

		socketServidorCentral = new Socket(InetAddress.getByName(ConstantesGerais.IP_SERVIDOR_CENTRAL),
				ConstantesGerais.PORTA_SERVIDOR_CENTRAL, InetAddress.getByName(ipMaquina), 0);

		Thread.sleep(1000);

		socketServidorChat = new Socket(InetAddress.getByName(ConstantesGerais.IP_SERVIDOR_CHAT),
				ConstantesGerais.PORTA_SERVIDOR_CHAT, InetAddress.getByName(ipMaquina), 0);

		fluxoSaidaDados = new ObjectOutputStream(socketServidorChat.getOutputStream());
		fluxoSaidaDados.flush();
	}

	public Socket getSocket() {
		return socketServidorChat;
	}

	public ObjectOutputStream getFluxoSaidaDados() {
		return fluxoSaidaDados;
	}

	public static ServidorPainelPrincipalAnuncios getInstance() {

		if (instancia == null)
			return instancia = new ServidorPainelPrincipalAnuncios();

		return instancia;
	}
}
