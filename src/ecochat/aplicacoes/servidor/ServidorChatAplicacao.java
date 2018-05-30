package ecochat.aplicacoes.servidor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import ecochat.entidades.DadoCompartilhado;
import ecochat.interfaces.telas.UIJanelaChat;

public class ServidorChatAplicacao {

	private static Socket socket;
	private static ObjectOutputStream fluxoSaidaDados;
	private static ServidorChatAplicacao instancia;

	private ServidorChatAplicacao() {

		// TODO PRECISA DESCOMENTAR AQUI
		try {
			inicializarSockets();
			UIJanelaChat.getInstance();
			iniciarLeituraMensagemServidor();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void inicializarSockets() throws UnknownHostException, IOException {

		String ipMaquina = "127.0.0.2";//Inet4Address.getLocalHost().getHostAddress();
		
		 socket = new Socket(InetAddress.getByName("127.0.0.1"), 
				 								   12345, 
				 								   InetAddress.getByName(ipMaquina), 0);
		 fluxoSaidaDados = new ObjectOutputStream(socket.getOutputStream());
	}

	public void enviarMensagemAoServidor(final DadoCompartilhado dadoCompartilhado) {

		new Thread() {
			public void run() {
				try {
					dadoCompartilhado.setEmailEntrega("127.0.0.3");

					if (dadoCompartilhado.getArquivo() != null) {
						Thread.sleep(2000);
						fluxoSaidaDados.writeObject(dadoCompartilhado);
						UIJanelaChat.getInstance().trocarLoadingPorImagemArquivo("Você Enviou");
					} else {
						fluxoSaidaDados.writeObject(dadoCompartilhado);
					}

					fluxoSaidaDados.flush();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	private static void iniciarLeituraMensagemServidor() {
		new Thread() {
			public void run() {

				try {
					while (true) {

						ObjectInputStream fluxoEntradaDados = new ObjectInputStream(socket.getInputStream());
						DadoCompartilhado dadoCompartilhado = (DadoCompartilhado) fluxoEntradaDados.readObject();
						UIJanelaChat.getInstance().receberMensagem(dadoCompartilhado);

						if (dadoCompartilhado.getArquivo() != null) {
							InputStream entradaArquivo = null;
							OutputStream saidaArquivo = null;

							try {
								entradaArquivo = new FileInputStream(dadoCompartilhado.getArquivo());
								saidaArquivo = new FileOutputStream(
										new File("C:\\Users\\richard.divino\\Desktop\\Cliente\\extratoTres.mp4"));

								byte[] memoriaTemporaria = new byte[1024 * 50];
								int tamanho;
								while ((tamanho = entradaArquivo.read(memoriaTemporaria)) > 0) {
									saidaArquivo.write(memoriaTemporaria, 0, tamanho);
								}
								System.out.println("Recebido com sucesso!");
							} catch (Exception ex) {
								System.err.println(ex.getMessage());
							}
						}
					}
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
					System.err.println(e.getMessage());
				} catch (Exception ex) {
					System.err.println(ex.getMessage());
				}
			}
		}.start();
	}

	public static ServidorChatAplicacao getInstance() {

		if (instancia == null)
			return instancia = new ServidorChatAplicacao();

		return instancia;
	}
}
