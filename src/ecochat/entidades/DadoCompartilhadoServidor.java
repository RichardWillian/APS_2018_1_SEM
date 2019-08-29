package ecochat.entidades;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class DadoCompartilhadoServidor implements Serializable{

	private List<DadoAnuncio> anuncios;
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

	public List<DadoAnuncio> getAnuncio() {
		return anuncios;
	}

	public void setAnuncio(List<DadoAnuncio> anuncio) {
		this.anuncios = anuncio;
	}
}
