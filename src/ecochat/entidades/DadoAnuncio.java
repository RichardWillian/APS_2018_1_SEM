package ecochat.entidades;

import java.io.File;
import java.io.Serializable;

import javax.swing.Icon;

@SuppressWarnings("serial")
public class DadoAnuncio implements Serializable{
	
	private String titulo;
	private String descricao;
	private String categoria;
	private Icon   imagem;
	
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Icon getImagem() {
		return imagem;
	}
	
	public void setImagem(Icon icon) {
		this.imagem = icon;
	}
	
}
