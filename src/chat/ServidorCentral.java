package chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import entidades.DadoCompartilhado;
import telas.UIJanelaServidorCentralChat;

public class ServidorCentral {

	private static ServerSocket socketServidorCentral;
	private static List<Socket> socketsConectados;
	private static ServidorCentral instancia;

	public static void main(String[] args) {

		try {

			UIJanelaServidorCentralChat.getInstance();

		} catch (Exception e) {
			System.err.println("Ops! " + e.getMessage());
		}
	}

	public void iniciarServidor() {

		try {

			socketServidorCentral = new ServerSocket(12345);
			socketsConectados = new ArrayList<Socket>();

			do {
				
				if (!socketServidorCentral.isClosed()) {
					
					UIJanelaServidorCentralChat.getInstance().mostrarMensagem("   ---===== Servidor Conectado =====---");
					Socket socket = socketServidorCentral.accept();
					
					ObjectInputStream fluxoEntradaDados = new ObjectInputStream(socket.getInputStream());
					UIJanelaServidorCentralChat.getInstance().mostrarMensagem("Cliente "+ socket.getLocalAddress().getHostAddress() + " se conectou!\n");
					socketsConectados.add(socket);
					lerMensagemDoCliente(fluxoEntradaDados);
				}
				
			} while (true);
		} catch (IOException ioE) {
			System.err.println(ioE.getMessage());
		}
	}

	public void desligarServidor() {
		try {
			socketServidorCentral.close();
			UIJanelaServidorCentralChat.getInstance().mostrarMensagem(" ---===== Servidor Desconectado =====---");
		} catch (IOException ioE) {
			System.err.println("Falha ao desligar o servidor\n\n" + ioE.getMessage());
		}
	}

	private static void lerMensagemDoCliente(final ObjectInputStream fluxoEntradaDados) {
		new Thread() {
			@SuppressWarnings("unused")
			public void run() {
				String mensagemEntrada = "";

				try {
					while (true) {

						if (!socketServidorCentral.isClosed()) {
							Socket socketQueReceberaMensagem = null; // socketsConectados.stream()
							// .filter(x ->
							// x.getLocalAddress().getHostAddress().equals(paraQuemEnviar))
							// .findFirst().get();

							DadoCompartilhado dadoCompartilhado = (DadoCompartilhado) fluxoEntradaDados.readObject();

							for (Socket socketConectado : socketsConectados) {
								if (socketConectado.getLocalAddress().getHostAddress()
										.equals(dadoCompartilhado.getEmailEntrega())) {
									socketQueReceberaMensagem = socketConectado;
								}
							}

							ObjectOutputStream fluxoSaidaDados = new ObjectOutputStream(
									socketQueReceberaMensagem.getOutputStream());
							fluxoSaidaDados.writeObject(dadoCompartilhado);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}

	public static ServidorCentral getInstance() {

		if (instancia == null)
			return instancia = new ServidorCentral();

		return instancia;
	}
}
