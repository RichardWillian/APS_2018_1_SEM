package ecochat.aplicacoes.servidor.controle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import ecochat.entidades.DadoCompartilhado;
import ecochat.interfaces.telas.UIJanelaChat;
import ecochat.utilitarios.Utilitaria;

public class ControleChatAplicacao {

	private static ControleChatAplicacao instancia;
	private List<DadoCompartilhado> mensagensNaoEnviadas;

	private ControleChatAplicacao() {
		UIJanelaChat.getInstance();
		mensagensNaoEnviadas = new ArrayList<DadoCompartilhado>();
		lerMensagemServidor();
	}

	public void enviarMensagemAoServidor(final DadoCompartilhado dadoCompartilhado) {

		try {

			if (ControlePainelPrincipalAnuncios.getInstance().servidorCentralIsClosed()) {

				if (mensagensNaoEnviadas.size() == 0) {
					
					mensagensNaoEnviadas.add(dadoCompartilhado);
					
					new Thread() {
						@SuppressWarnings("static-access")
						public void run() {

							while (true) {

								try {

									if (!ControlePainelPrincipalAnuncios.getInstance().servidorCentralIsClosed()) {

										for (int i = 0; i < mensagensNaoEnviadas.size(); i++)
											enviarMensagemDestinatario(mensagensNaoEnviadas.get(i));

										mensagensNaoEnviadas = new ArrayList<DadoCompartilhado>();
										Thread.currentThread().interrupted();
										break;
									}

								} catch (IOException | InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
					}.start();
				} else
					mensagensNaoEnviadas.add(dadoCompartilhado);

			} else
				enviarMensagemDestinatario(dadoCompartilhado);

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void enviarMensagemDestinatario(final DadoCompartilhado dadoCompartilhado) {

		try {
			if (dadoCompartilhado.getArquivo() != null) {
				Thread.sleep(2000);
				UIJanelaChat.getInstance().trocarLoadingPorImagemArquivo("Você Enviou", dadoCompartilhado.getArquivo());
			}

			ObjectOutputStream fluxoSaidaDados = ControlePainelPrincipalAnuncios.getInstance().getFluxoSaidaDados();
			dadoCompartilhado.setRemetente(ControlePainelPrincipalAnuncios.getInstance().getIpAplicacao());

			fluxoSaidaDados.writeObject(dadoCompartilhado);
			fluxoSaidaDados.flush();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void lerMensagemServidor() {
		new Thread() {
			@SuppressWarnings("static-access")
			public void run() {

				try {
					while (true) {

						ObjectInputStream fluxoEntradaDados = new ObjectInputStream(
								ControlePainelPrincipalAnuncios.getInstance().getSocket().getInputStream());

						Object leituraObjeto = fluxoEntradaDados.readObject();

						if (UIJanelaChat.getInstance().isFocusableWindow()) {
							UIJanelaChat.setMensagemNaFila(false);
						}

						DadoCompartilhado dadoCompartilhado = (DadoCompartilhado) leituraObjeto;
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
										new File(Utilitaria.recuperarPastaDownload() + Utilitaria.gerarNomeArquivo()
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
				} catch (Exception ex) {
					ex.printStackTrace();
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
