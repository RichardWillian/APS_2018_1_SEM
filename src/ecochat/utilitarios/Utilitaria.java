package ecochat.utilitarios;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;

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

	public static boolean identificarTipoArquivo(File arquivoEnvio) {

		String tipoMIMEArquivo = null;
		try {
			tipoMIMEArquivo = Files.probeContentType(arquivoEnvio.toPath());
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
	public static boolean verificarAutenticacaoUsuario(String email, String senha) {

		try {
			Socket socketAutenticacao = new Socket(InetAddress.getByName("127.255.255.254"), 12346,
					InetAddress.getByName("127.0.0.3"), 0);

			ObjectOutputStream fluxoSaidaDadosAutenticacao = new ObjectOutputStream(
					socketAutenticacao.getOutputStream());

			DadoAutenticacao dadoAutenticacao = new DadoAutenticacao();

			dadoAutenticacao.setEmail(email);
			dadoAutenticacao.setSenha(senha);

			fluxoSaidaDadosAutenticacao.writeObject(dadoAutenticacao);
			fluxoSaidaDadosAutenticacao.flush();

			ObjectInputStream fluxoEntradaDados = new ObjectInputStream(socketAutenticacao.getInputStream());
			boolean isUsuarioAutenticado = false;
			try {
				isUsuarioAutenticado = (boolean) fluxoEntradaDados.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			return isUsuarioAutenticado;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void cadastrarUsuario(String nome, String email, String senha) {
		
		UsuarioDAO.addUsuario(nome, email, senha);
	}
}
