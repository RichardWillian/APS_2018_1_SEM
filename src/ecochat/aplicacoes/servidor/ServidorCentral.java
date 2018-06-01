package ecochat.aplicacoes.servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;

import ecochat.entidades.DadoCompartilhado;
import ecochat.interfaces.telas.UIJanelaServidorCentral;
import ecochat.utilitarios.ConstantesGerais;

public class ServidorCentral {

	private static ServerSocket socketServidorCentral;
	private static List<Socket> socketsConectados;
	private static ServidorCentral instancia;
	// private static Map<Socket, DadoCompartilhado>
	// informacoesSocketsConectados = new HashMap<Socket, DadoCompartilhado>();

	public static void main(String[] args) {

		try {
			UIJanelaServidorCentral.getInstance();
		} catch (HibernateException exception) {
			exception.printStackTrace();
		} catch (Exception e) {
			System.err.println("Ops! " + e.getMessage() + "\n");
		}
	}

	@SuppressWarnings("unused")
	private Object ObjectOutputStream;

	public void iniciarServidor() {

		try {
			ServidorAutenticacao.getInstance().iniciarServidor();
			socketServidorCentral = new ServerSocket(ConstantesGerais.PORTA_SERVIDOR_CENTRAL,
					ConstantesGerais.QUANTIDADE_MAXIMA_CONECTADOS,
					InetAddress.getByName(ConstantesGerais.IP_SERVIDOR_CENTRAL));

			socketsConectados = new ArrayList<Socket>();
			UIJanelaServidorCentral.getInstance().mostrarMensagem("   ---===== Servidor Conectado =====---");
			do {
				Socket socket = socketServidorCentral.accept();
				atualizarUsuariosOnlines(socket.getInetAddress().getHostAddress());

				ObjectInputStream fluxoEntradaDados = new ObjectInputStream(socket.getInputStream());

				UIJanelaServidorCentral.getInstance().mostrarConectados(socket.getInetAddress().getHostAddress());
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
			UIJanelaServidorCentral.getInstance().mostrarMensagem(" ---===== Servidor Desconectado =====---");

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
									// Socket socketDesconectado =
									// socketConectado;
									// socketsMensagensPendentes.put(socketDesconectado,
									// dadoCompartilhado);
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

	public void atualizarUsuariosOnlines(final String ipSocketConectado) {

		new Thread() {
			public void run() {
				List<Socket> socketsConetadosCopia = new ArrayList<Socket>(socketsConectados);
				try {
					for (Socket socketConectado : socketsConetadosCopia) {

						if (!socketConectado.getInetAddress().getHostAddress().equals(ipSocketConectado)) {
							ObjectOutputStream fluxoSaidaDados = new ObjectOutputStream(socketConectado.getOutputStream());
							fluxoSaidaDados.writeObject(ipSocketConectado);
						}
					}
				} catch (IOException e) {
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
