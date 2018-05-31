package ecochat.aplicacoes.cliente;

import ecochat.interfaces.telas.UIJanelaLogin;
import ecochat.utilitarios.Utilitaria;

public class Aplicacao {

	public static void main(String[] args) {
		
		Utilitaria.CriarDiretorio(System.getProperty("user.dir")+ "\\DownloadsEcochat");
		UIJanelaLogin.getInstance();
	}
}