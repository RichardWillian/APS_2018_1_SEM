package ecochat.aplicacoes.servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import ecochat.entidades.DadoCompartilhado;
import ecochat.interfaces.telas.UIJanelaServidorCentralChat;

public class ServidorCentral {

	private static ServerSocket socketServidorCentral;
	private static List<Socket> socketsConectados;
	private static ServidorCentral instancia;
	private static Map<Socket, DadoCompartilhado> socketsMensagensPendentes = new HashMap<Socket, DadoCompartilhado>();
	private static final String IP_SERVIDOR = "127.0.0.1";

	public static void main(String[] args) {

		try {
			UIJanelaServidorCentralChat.getInstance();
		} 
		catch(HibernateException exception){
		     exception.printStackTrace();
		}catch (Exception e) {
			System.err.println("Ops! " + e.getMessage() + "\n");
		}
	}

	public void iniciarServidor() {

		try {
			ServidorAutenticacao.getInstance().iniciarServidor();
			socketServidorCentral = new ServerSocket(12345, 20, InetAddress.getByName(IP_SERVIDOR));
			socketsConectados = new ArrayList<Socket>();
			UIJanelaServidorCentralChat.getInstance().mostrarMensagem("   ---===== Servidor Conectado =====---");
			do {
				Socket socket = socketServidorCentral.accept();

				ObjectInputStream fluxoEntradaDados = new ObjectInputStream(socket.getInputStream());

				UIJanelaServidorCentralChat.getInstance().mostrarConectados(socket.getInetAddress().getHostAddress());
				socketsConectados.add(socket);

				if (!socketServidorCentral.isClosed())
					lerMensagemDoCliente(fluxoEntradaDados);

			} while (true);
		} catch (IOException ioE) {
			System.err.println(ioE.getMessage());
		}
	}

	public void desligarServidor() {
		try {

			for (Socket socketConectado : socketsConectados) {
				socketConectado.close();
			}

			socketServidorCentral.close();
			UIJanelaServidorCentralChat.getInstance().mostrarMensagem(" ---===== Servidor Desconectado =====---");

		} catch (IOException ioE) {
			System.err.println("Falha ao desligar o servidor\n\n" + ioE.getMessage());
		}
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
									ServidorCentral.getInstance().enviarMensagem(socketQueReceberaMensagem,
											dadoCompartilhado);
								} else {
									Socket socketDesconectado = socketConectado;
									socketsMensagensPendentes.put(socketDesconectado, dadoCompartilhado);
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

	public static ServidorCentral getInstance() {

		if (instancia == null)
			return instancia = new ServidorCentral();

		return instancia;
	}
}
