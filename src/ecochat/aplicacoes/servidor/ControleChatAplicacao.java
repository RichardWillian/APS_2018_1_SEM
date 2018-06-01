package ecochat.aplicacoes.servidor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;

import ecochat.entidades.DadoCompartilhado;
import ecochat.interfaces.telas.UIJanelaChat;
import ecochat.utilitarios.Utilitaria;

public class ControleChatAplicacao {

	private static ControleChatAplicacao instancia;

	private ControleChatAplicacao() {

		UIJanelaChat.getInstance();
		lerMensagemServidor();
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

					ServidorPainelPrincipalAnuncios.getInstance().getFluxoSaidaDados().writeObject(dadoCompartilhado);
					ServidorPainelPrincipalAnuncios.getInstance().getFluxoSaidaDados().flush();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	private static void lerMensagemServidor() {
		new Thread() {
			@SuppressWarnings("static-access")
			public void run() {

				try {
					while (true) {

						ObjectInputStream fluxoEntradaDados = new ObjectInputStream(
								ServidorPainelPrincipalAnuncios.getInstance().getSocket().getInputStream());

						DadoCompartilhado dadoCompartilhado = (DadoCompartilhado) fluxoEntradaDados.readObject();
						UIJanelaChat.getInstance().receberMensagem(dadoCompartilhado.getMensagem());

						if (dadoCompartilhado.getArquivo() != null) {

							UIJanelaChat.getInstance().adicionarAnimacaoArquivo();

							InputStream entradaArquivo = null;
							OutputStream saidaArquivo = null;

							try {
								File arquivo = dadoCompartilhado.getArquivo();

								arquivo.createTempFile(Utilitaria.recuperarPastaDownload(), "");
								entradaArquivo = new FileInputStream(arquivo);
								saidaArquivo = new FileOutputStream(
										new File(Utilitaria.recuperarPastaDownload()
												+ Utilitaria.gerarNomeArquivo()
												+ Utilitaria.recuperarExtensaoArquivo(arquivo)));
								
								this.sleep(2000);
								
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

	public static ControleChatAplicacao getInstance() {

		if (instancia == null)
			return instancia = new ControleChatAplicacao();

		return instancia;
	}
}
