package ecochat.interfaces.telas;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import ecochat.aplicacoes.telas.JanelaBase;
import ecochat.utilitarios.Utilitaria;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;

@SuppressWarnings("serial")
public class UIJanelaCadastrar extends JanelaBase {
	private JLabel nome, email, senha;
	private JTextField tnome, temail;
	private JPasswordField psenha;
	private JButton ok, bntVoltar;

	public UIJanelaCadastrar() {

		this.setBounds(500, 500, 251, 350);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		nome = new JLabel("Nome:");
		nome.setFont(new Font("Tahoma", Font.BOLD, 13));
		nome.setForeground(Color.WHITE);
		senha = new JLabel("Senha: ");
		senha.setFont(new Font("Tahoma", Font.BOLD, 13));
		senha.setForeground(Color.WHITE);
		temail = new JTextField();
		psenha = new JPasswordField();
		ok = new JButton("OK");
		bntVoltar = new JButton("Voltar");
		senha.setBounds(20, 200, 50, 20);
		temail.setBounds(70, 160, 150, 20);
		nome.setBounds(20, 120, 50, 20);
		psenha.setBounds(70, 200, 150, 20);
		ok.setBounds(26, 250, 90, 20);
		bntVoltar.setBounds(130, 250, 90, 20);

		getContentPane().setLayout(null);

		getContentPane().add(nome);
		getContentPane().add(senha);
		email = new JLabel("Email:");
		email.setFont(new Font("Tahoma", Font.BOLD, 13));
		email.setForeground(Color.WHITE);

		email.setBounds(20, 160, 50, 20);
		getContentPane().add(email);
		email.addKeyListener(this);
		tnome = new JTextField();
		tnome.setBounds(70, 120, 150, 20);
		getContentPane().add(tnome);
		tnome.addKeyListener(this);
		getContentPane().add(psenha);
		getContentPane().add(temail);
		getContentPane().add(ok);
		getContentPane().add(bntVoltar);

		JLabel label_1 = new JLabel();
		label_1.setIcon(
				new ImageIcon(UIJanelaCadastrar.class.getResource("/ecochat/interfaces/telas/imagens/Logo1.png")));
		label_1.setBounds(-11, 0, 98, 96);
		getContentPane().add(label_1);

		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(
				UIJanelaCadastrar.class.getResource("/ecochat/interfaces/telas/imagens/background4.jpg")));
		label.setBounds(0, 0, 245, 321);
		getContentPane().add(label);
		getContentPane().addKeyListener(this);

		bntVoltar.addActionListener(this);
		ok.addActionListener(this);
		senha.addKeyListener(this);
		temail.addKeyListener(this);
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
		if (e.getSource() == bntVoltar) {
			this.dispose();
		} else if (e.getSource() == ok) {
			cadastrarUsuario();
		}
	}

	private void cadastrarUsuario() {
		String nome = tnome.getText();
		String email = temail.getText();
		String senha = new String(psenha.getPassword());

		if (!(email == null || email.equals("")) && !(senha == null || senha.equals(""))
				&& !(nome == null || nome.equals(""))) {
			Utilitaria.cadastrarUsuario(nome, email, senha);
			UIJanelaLogin.getInstance();
			JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
			this.dispose();
		} else {
			boolean nenhumCampoPreenchido = (email == null
					|| email.equals("") && (senha == null || senha.equals("") && (nome == null || nome.equals(""))));

			if (nenhumCampoPreenchido)
				JOptionPane.showMessageDialog(null, "Ops! Você se esqueceu de informar seus dados");
			else {
				if (email == null || email.equals(""))
					JOptionPane.showMessageDialog(null, "Ops! Você se esqueceu de preencher seu \"Login\"");
				else if (senha == null || senha.equals(""))
					JOptionPane.showMessageDialog(null, "Ops! Você se esqueceu de informar sua \"Senha\"");
				else if (nome == null || nome.equals(""))
					JOptionPane.showMessageDialog(null, "Ops! Você se esqueceu de informar sua \"Nome\"");
			}
		}
	}
}