package ecochat.utilitarios;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Random;

import javax.swing.JLabel;

import ecochat.dao.modelos.usuario.UsuarioDAO;
import ecochat.entidades.DadoAutenticacao;

public class Utilitaria {

	public static JLabel quebrarLinhas(String mensagem) {
		JLabel lblMensagem;

		if (mensagem.length() > 40) {

			StringBuilder stringFormatada = new StringBuilder();
			StringBuilder caractesMensagem = new StringBuilder(mensagem);
			StringBuilder caracteresAteAqui = new StringBuilder();
			StringBuilder caracteresJaConcatenados = new StringBuilder();

			for (int i = 0; i < mensagem.length(); i++) {

				caracteresAteAqui.append(caractesMensagem.charAt(i));

				if (caractesMensagem.length() - caracteresJaConcatenados.length() < 30) {

					for (int k = caracteresJaConcatenados.length(); k < caractesMensagem.length(); k++) {
						stringFormatada.append(caractesMensagem.charAt(k));
						i = mensagem.length();
					}
				}

				if (i != 0 && i % 30 == 0) {

					if (caractesMensagem.charAt(i) != ' ') {

						for (int j = i + 1; j < mensagem.length(); j++) {

							caracteresAteAqui.append(caractesMensagem.charAt(j));

							if (caractesMensagem.charAt(j) == ' ') {

								stringFormatada.append(caracteresAteAqui + "<br>");
								caracteresJaConcatenados.append(caracteresAteAqui);
								if (caractesMensagem.length() - caracteresJaConcatenados.length() <= 30) {

									for (int k = caracteresJaConcatenados.length(); k < caractesMensagem
											.length(); k++) {
										stringFormatada.append(caractesMensagem.charAt(k));

									}
									i = mensagem.length();
									j = mensagem.length();
								} else {

									caracteresAteAqui = new StringBuilder();
									i = j;
									break;
								}
							} else {

								if (j % 11 == 0) {

									stringFormatada.append(caracteresAteAqui + "<br>");
									caracteresJaConcatenados.append(caracteresAteAqui);

									if (caractesMensagem.length() - caracteresJaConcatenados.length() <= 30) {

										for (int k = caracteresJaConcatenados.length(); k < caractesMensagem
												.length(); k++) {
											stringFormatada.append(caractesMensagem.charAt(k));

										}
										i = mensagem.length();
										j = mensagem.length();
									} else {

										caracteresAteAqui = new StringBuilder();
										i = j;
										break;
									}
								}
							}

						}
					} else {

						stringFormatada.append(caracteresAteAqui + "<br>");
						caracteresJaConcatenados.append(caracteresAteAqui);
						caracteresAteAqui = new StringBuilder();
					}
				}
			}

			lblMensagem = new JLabel("<html><p>" + stringFormatada + "</html>");

		} else
			lblMensagem = new JLabel("<html><p>" + mensagem + "</html>");

		return lblMensagem;
	}

	public static String recuperarExtensaoArquivo(File arquivo) {

		String caminhoArquivo = arquivo.getAbsolutePath();
		String extensao = "";

		int i = caminhoArquivo.lastIndexOf('.');
		int p = Math.max(caminhoArquivo.lastIndexOf('/'), caminhoArquivo.lastIndexOf('\\'));

		if (i > p) {
			extensao = caminhoArquivo.substring(i + 1);
		}

		return "." + extensao;
	}

	public static boolean identificarTipoArquivo(File arquivo) {

		String tipoMIMEArquivo = null;
		try {
			tipoMIMEArquivo = Files.probeContentType(arquivo.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		String tipoArquivo = tipoMIMEArquivo.split("/")[0];
		if (tipoArquivo.equals("image"))
			return true;
		else
			return false;
	}

	@SuppressWarnings("resource")
	public static boolean verificarAutenticacaoUsuario(String email, String senha) throws Exception {
		try {
			Socket socketAutenticacao = new Socket(InetAddress.getByName(ConstantesGerais.IP_SERVIDOR_AUTENTICACAO),
					ConstantesGerais.PORTA_SERVIDOR_AUTENTICACAO,
					InetAddress.getByName(ConstantesGerais.IP_FIXO_REQUISICAO_AUTENTICACAO), 0);

			ObjectOutputStream fluxoSaidaDadosAutenticacao = new ObjectOutputStream(
					socketAutenticacao.getOutputStream());

			DadoAutenticacao dadoAutenticacao = new DadoAutenticacao();

			dadoAutenticacao.setEmail(email);
			dadoAutenticacao.setSenha(senha);

//			Thread temporizador = new Thread() {
//				public void run() {
//					try {
//						for (int i = 0; i < 15; i++) {
//							Thread.sleep(500);
//						}
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			};

			fluxoSaidaDadosAutenticacao.writeObject(dadoAutenticacao);

			fluxoSaidaDadosAutenticacao.flush();

			//temporizador.start();
			ObjectInputStream fluxoEntradaDados = new ObjectInputStream(socketAutenticacao.getInputStream());
			boolean isUsuarioAutenticado = false;

			isUsuarioAutenticado = (boolean) fluxoEntradaDados.readObject();
			//temporizador.interrupt();
			return isUsuarioAutenticado;

		} catch (Exception e) {
			throw e;
		}
	}

	public static void cadastrarUsuario(String nome, String email, String senha) {
		UsuarioDAO.cadastrarUsuario(nome, email, senha);
	}

	public static void CriarDiretorio(String caminho) {
		File diretorio = new File(caminho);

		if (!diretorio.exists())
			diretorio.mkdirs();
	}

	public static String recuperarPastaDownload() {
		return System.getProperty("user.dir") + "\\DownloadsEcochat\\";
	}

	public static String gerarNomeArquivo() {

		int tamanhoNome = new Random().nextInt(30) + 5;
		Random rand = new Random();
		char[] caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

		StringBuffer nomeArquivo = new StringBuffer();
		for (int i = 0; i <= tamanhoNome; i++) {
			int posicaoCaracater = rand.nextInt(caracteres.length);
			nomeArquivo.append(caracteres[posicaoCaracater]);
		}

		return nomeArquivo.toString();
	}

	public static String criarHostAleatorio() {

		Random numeroAleatorio = new Random();
		int minimo = 1;
		int maximo = 254;
		Integer host = -1;

		while (host <= 0 || host > 254) {
			host = numeroAleatorio.nextInt(maximo - minimo) + 1;
		}

		return host.toString();
	}

	public static String criarIpAleatorio() {

		return "127" + "." + criarHostAleatorio() + "." + criarHostAleatorio() + "." + criarHostAleatorio();
	}
}
