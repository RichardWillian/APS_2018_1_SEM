package ecochat.utilitarios;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import ecochat.aplicacoes.telas.JanelaBase;

@SuppressWarnings("serial")
public class Modificar extends JanelaBase {

	private JFrame tela;

	public Modificar(JFrame obtela) {
		tela = obtela;
	}

	private void destruirTela() {
		tela.dispose();
	}

	@SuppressWarnings("unused")
	private void ocultaTela() {
		tela.setVisible(false);
	}

	public void windowClosing(WindowEvent e) {
		destruirTela();
	}
	
	public void actionPerformed(ActionEvent e) {
		destruirTela();
	}

}
