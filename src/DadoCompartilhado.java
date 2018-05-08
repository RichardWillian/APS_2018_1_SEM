import java.io.Serializable;

@SuppressWarnings("serial")
public class DadoCompartilhado implements Serializable{

	private String mensagem;
	private String emailEntrega;
	
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
}
