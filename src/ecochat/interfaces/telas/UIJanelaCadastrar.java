package ecochat.interfaces.telas;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

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

		this.setBounds(500, 500, 251, 350);
		this.setLocationRelativeTo(null);

		nome = new JLabel("Nome:");
		email = new JLabel("Email:");
		senha = new JLabel("Senha: ");
		tnome = new JTextField();
		temail = new JTextField();
		psenha = new JPasswordField();
		ok = new JButton("OK");
		exit = new JButton("Sair");

		email.setBounds(20, 84, 50, 20);
		senha.setBounds(20, 132, 50, 20);
		temail.setBounds(70, 84, 150, 20);
		nome.setBounds(20, 42, 50, 20);
		tnome.setBounds(70, 42, 150, 20);
		psenha.setBounds(70, 132, 150, 20);
		ok.setBounds(26, 183, 90, 20);
		exit.setBounds(130, 183, 90, 20);

		getContentPane().setLayout(null);

		getContentPane().add(nome);
		getContentPane().add(senha);
		getContentPane().add(psenha);
		getContentPane().add(tnome);
		getContentPane().add(temail);
		getContentPane().add(ok);
		getContentPane().add(exit);
		getContentPane().add(email);
		// ImageIcon fundolg = new
		// ImageIcon(Login.class.getResource("/fundo.jpg"));
		// fundo = fundolg.getImage();

		ok.addActionListener(this);
		exit.addActionListener(this);
		// cadastrar.addActionListener(this);
		this.addWindowListener(this);
		this.addKeyListener(this);
		repaint();
	}

	public void keyPressed(KeyEvent ke) {

		if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
			cadastrarUsuario();
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exit) {
			int b = JOptionPane.showConfirmDialog(null, "Deseja Sair?", null, JOptionPane.YES_NO_OPTION);
			if (b == JOptionPane.YES_OPTION) {
				this.dispose();
			}
		} else if (e.getSource() == ok) {
			cadastrarUsuario();
		}
	}

	private void cadastrarUsuario() {
		String nome = tnome.getText();
		String email = temail.getText();
		String senha = new String(psenha.getPassword());
		Utilitaria.cadastrarUsuario(nome, email, senha);
		UIJanelaLogin.getInstance();
		JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
		this.dispose();
	}
}