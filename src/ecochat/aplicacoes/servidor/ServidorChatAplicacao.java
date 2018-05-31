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
import java.nio.file.Files;

import ecochat.entidades.DadoCompartilhado;
import ecochat.interfaces.telas.UIJanelaChat;
import ecochat.utilitarios.Utilitaria;

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

		String ipMaquina = "127.0.0.2";// Inet4Address.getLocalHost().getHostAddress();

		socket = new Socket(InetAddress.getByName("127.0.0.1"), 12345, InetAddress.getByName(ipMaquina), 0);

		fluxoSaidaDados = new ObjectOutputStream(socket.getOutputStream());
	}

	public void enviarMensagemAoServidor(final DadoCompartilhado dadoCompartilhado) {

		new Thread() {
			public void run() {
				try {
					dadoCompartilhado.setEmailEntrega("127.0.0.3");

					if (dadoCompartilhado.getArquivo() != null) {
						Thread.sleep(2000);
						UIJanelaChat.getInstance().trocarLoadingPorImagemArquivo("Você Enviou",
								dadoCompartilhado.getArquivo());
					}

					fluxoSaidaDados.writeObject(dadoCompartilhado);
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
			@SuppressWarnings("static-access")
			public void run() {

				try {
					while (true) {

						// TODO MEXER AQUI
						ObjectInputStream fluxoEntradaDados = new ObjectInputStream(socket.getInputStream());
						DadoCompartilhado dadoCompartilhado = (DadoCompartilhado) fluxoEntradaDados.readObject();

						if (dadoCompartilhado.getArquivo() != null) {

							UIJanelaChat.getInstance().adicionarAnimacaoArquivo();

							InputStream entradaArquivo = null;
							OutputStream saidaArquivo = null;

							try {
								File arquivo =dadoCompartilhado.getArquivo(); 
								
								arquivo.createTempFile(Utilitaria.recuperarPastaDownload(), "");
								entradaArquivo = new FileInputStream(arquivo);
								saidaArquivo = new FileOutputStream(new File(Utilitaria.recuperarPastaDownload() 
																		   + Utilitaria.gerarNomeArquivo()
																		   + Utilitaria.recuperarExtensaoArquivo(arquivo) ));
								
								byte[] memoriaTemporaria = new byte[1024 * 50];
								int tamanho;
								while ((tamanho = entradaArquivo.read(memoriaTemporaria)) > 0) {
									saidaArquivo.write(memoriaTemporaria, 0, tamanho);
								}

								UIJanelaChat.getInstance().trocarLoadingPorImagemArquivo("Você Recebeu",
										dadoCompartilhado.getArquivo());
							} catch (Exception ex) {
								System.err.println(ex.getMessage());
							} finally {

								entradaArquivo.close();
								saidaArquivo.flush();
								saidaArquivo.close();
							}
						}

						UIJanelaChat.getInstance().receberMensagem(dadoCompartilhado.getMensagem());
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
