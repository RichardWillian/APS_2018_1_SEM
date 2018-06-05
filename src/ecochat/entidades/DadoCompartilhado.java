package ecochat.entidades;

import java.io.File;
import java.io.Serializable;

@SuppressWarnings("serial")
public class DadoCompartilhado implements Serializable {

	private String mensagem;
	private String remetente;
	private String destinatario;
	private File arquivo;

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getDestinatario() {
		return destinatario = "127.36.76.222";
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public File getArquivo() {
		return arquivo;
	}

	public void setArquivo(File arquivo) {
		this.arquivo = arquivo;
	}

	public String getRemetente() {
		return remetente;
	}

	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}
}
