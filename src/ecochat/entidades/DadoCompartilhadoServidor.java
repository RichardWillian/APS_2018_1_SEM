package ecochat.entidades;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DadoCompartilhadoServidor implements Serializable{

	private DadoAnuncio anuncio;
	private String ipUsuarioConectou = null;
	private DadoCompartilhado dadoCompartilhado;


	public String getIpUsuarioConectou() {
		return ipUsuarioConectou;
	}

	public void setIpUsuarioConectou(String ipUsuarioConectou) {
		this.ipUsuarioConectou = ipUsuarioConectou;
	}

	public DadoCompartilhado getDadoCompartilhado() {
		return dadoCompartilhado;
	}

	public void setDadoCompartilhado(DadoCompartilhado dadoCompartilhado) {
		this.dadoCompartilhado = dadoCompartilhado;
	}

	public DadoAnuncio getAnuncio() {
		return anuncio;
	}

	public void setAnuncio(DadoAnuncio anuncio) {
		this.anuncio = anuncio;
	}
}
