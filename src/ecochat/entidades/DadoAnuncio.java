package ecochat.entidades;

import java.io.File;
import java.io.Serializable;

@SuppressWarnings("serial")
public class DadoAnuncio implements Serializable{
	
	private String titulo;
	private String descricao;
	private String categoria;
	private String localizacao;
	private File   imagem;
	
	
	private String getTitulo() {
		return titulo;
	}
	private void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	private String getDescricao() {
		return descricao;
	}
	private void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	private String getCategoria() {
		return categoria;
	}
	private void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	private String getLocalizacao() {
		return localizacao;
	}
	private void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}
	private File getImagem() {
		return imagem;
	}
	
	private void setImagem(File imagem) {
		this.imagem = imagem;
	}
	
}
