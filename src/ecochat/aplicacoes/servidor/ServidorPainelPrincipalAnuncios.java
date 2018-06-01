package ecochat.aplicacoes.servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import ecochat.interfaces.telas.UIJanelaPrincipal;
import ecochat.utilitarios.ConstantesGerais;

public class ServidorPainelPrincipalAnuncios {

	private static Socket socket;
	private static ObjectOutputStream fluxoSaidaDados;
	private static ServidorPainelPrincipalAnuncios instancia;

	private ServidorPainelPrincipalAnuncios() {
		try {
			inicializarSockets();
			atualizarSocketsConectados();
			UIJanelaPrincipal.getInstance();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void atualizarSocketsConectados() {

		new Thread() {
			public void run() {
				while (true) {
					try {
						ObjectInputStream fluxoEntradaDados = new ObjectInputStream(socket.getInputStream());

						try {
							String ipSocketConectado = (String) fluxoEntradaDados.readObject();
							UIJanelaPrincipal.getInstance().adicionarUsuariosOnline(ipSocketConectado);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	private void inicializarSockets() throws UnknownHostException, IOException {

		String ipMaquina = "127.0.0.2";// Inet4Address.getLocalHost().getHostAddress();

		socket = new Socket(InetAddress.getByName(ConstantesGerais.IP_SERVIDOR_CENTRAL),
				ConstantesGerais.PORTA_SERVIDOR_CENTRAL, InetAddress.getByName(ipMaquina), 0);

		fluxoSaidaDados = new ObjectOutputStream(socket.getOutputStream());
	}

	public Socket getSocket() {
		return socket;
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
