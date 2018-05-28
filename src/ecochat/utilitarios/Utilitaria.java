package ecochat.utilitarios;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.JLabel;

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

					char caracterCorrenteUm = caractesMensagem.charAt(i);
					if (caracterCorrenteUm != ' ') {

						for (int j = i + 1; j < mensagem.length(); j++) {

							caracteresAteAqui.append(caractesMensagem.charAt(j));

							char caracterCorrenteDois = caractesMensagem.charAt(j);

							if (caracterCorrenteDois == ' ') {

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
}
