package entidades;
import java.io.File;
import java.io.Serializable;

@SuppressWarnings("serial")
public class DadoCompartilhado implements Serializable {

	private String mensagem;
	private String emailEntrega;
	private File arquivo;

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getEmailEntrega() {
		return emailEntrega;
	}

	public void setEmailEntrega(String emailEntrega) {
		this.emailEntrega = emailEntrega;
	}

	public File getArquivo() {
		return arquivo;
	}

	public void setArquivo(File arquivo) {
		this.arquivo = arquivo;
	}
}
