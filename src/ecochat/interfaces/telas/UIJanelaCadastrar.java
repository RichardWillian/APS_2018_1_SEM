package ecochat.interfaces.telas;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import ecochat.aplicacoes.telas.JanelaBase;
import ecochat.utilitarios.Utilitaria;

@SuppressWarnings("serial")
public class UIJanelaCadastrar extends JanelaBase {
	private JLabel nome, email, senha;
	private JTextField tnome, temail;
	private JPasswordField psenha;
	private JButton ok, exit;
	// private Image fundo;

	public UIJanelaCadastrar() {

		this.setBounds(500, 500, 350, 350);
		this.setLocationRelativeTo(null);

		nome = new JLabel("Nome:");
		email = new JLabel("Email:");
		senha = new JLabel("Senha: ");
		tnome = new JTextField();
		temail = new JTextField();
		psenha = new JPasswordField();
		ok = new JButton("OK");
		exit = new JButton("Sair");

		email.setBounds(20, 50, 50, 20);
		senha.setBounds(20, 75, 50, 20);
		temail.setBounds(70, 50, 150, 20);
		nome.setBounds(20, 25, 50, 20);
		tnome.setBounds(70, 25, 150, 20);
		psenha.setBounds(70, 75, 150, 20);
		ok.setBounds(20, 100, 90, 20);
		exit.setBounds(120, 100, 90, 20);

		this.setLayout(null);

		this.add(nome);
		this.add(senha);
		this.add(psenha);
		this.add(tnome);
		this.add(temail);
		this.add(ok);
		this.add(exit);
		this.add(email);
		// ImageIcon fundolg = new
		// ImageIcon(Login.class.getResource("/fundo.jpg"));
		// fundo = fundolg.getImage();

		ok.addActionListener(this);
		exit.addActionListener(this);
		// cadastrar.addActionListener(this);
		this.addWindowListener(this);
		repaint();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exit) {
			int b = JOptionPane.showConfirmDialog(null, "Deseja Sair?", null, JOptionPane.YES_NO_OPTION);
			if (b == JOptionPane.YES_OPTION) {
				this.setVisible(false);
			}
		} else if (e.getSource() == ok) {

			int b = JOptionPane.showConfirmDialog(null, "Cadastro realizado com sucesso! ", null,
					JOptionPane.OK_CANCEL_OPTION);
			if (b == JOptionPane.OK_OPTION) {

				String nome = tnome.getText();
				String email = temail.getText();
				String senha = new String(psenha.getPassword());

				Utilitaria.cadastrarUsuario(nome, email, senha);
				UIJanelaLogin.getInstance();
				this.dispose();
			}
		}
	}
}