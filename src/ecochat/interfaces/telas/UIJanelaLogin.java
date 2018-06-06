package ecochat.interfaces.telas;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import ecochat.aplicacoes.servidor.controle.ControlePainelPrincipalAnuncios;
import ecochat.aplicacoes.telas.JanelaBase;
import ecochat.utilitarios.Utilitaria;

@SuppressWarnings("serial")
public class UIJanelaLogin extends JanelaBase {

	private JLabel login, senha, image;
	private JTextField tlg;
	private JPasswordField psenha;
	private JButton ok, exit, cadastrar;

	private static UIJanelaLogin instancia;
	private JLabel lblFundoLogin;
	private JLabel label;

	public UIJanelaLogin() {
		
		this.setBounds(300, 300, 399, 375);
		this.setVisible(true);
		this.setLocationRelativeTo(null);

		image = new JLabel(new ImageIcon("/fundo.jpg"));
		login = new JLabel("Login:");
		login.setFont(new Font("Tahoma", Font.BOLD, 12));
		login.setForeground(Color.WHITE);
		senha = new JLabel("Senha: ");
		senha.setFont(new Font("Tahoma", Font.BOLD, 12));
		senha.setForeground(Color.WHITE);
		tlg = new JTextField();
		psenha = new JPasswordField();
		ok = new JButton("OK");
		exit = new JButton("Sair");
		cadastrar = new JButton("Cadastrar");

		login.setBounds(127, 100, 50, 20);
		senha.setBounds(127, 150, 50, 20);
		tlg.setBounds(127, 120, 150, 20);
		psenha.setBounds(127, 170, 150, 20);
		ok.setBounds(100, 221, 90, 20);
		exit.setBounds(215, 221, 90, 20);
		cadastrar.setBounds(158, 260, 100, 20);

		getContentPane().setLayout(null);
		getContentPane().add(login);
		getContentPane().add(senha);
		getContentPane().add(psenha);
		getContentPane().add(tlg);
		getContentPane().add(ok);
		getContentPane().add(exit);
		getContentPane().add(cadastrar);
		
		label = new JLabel("");
		label.setIcon(new ImageIcon(UIJanelaLogin.class.getResource("imagens/logo_ecochat.png")));
		label.setBounds(155, 20, 80, 95);
		getContentPane().add(label);
		getContentPane().add(image);

		lblFundoLogin = new JLabel();
		lblFundoLogin.setIcon(new ImageIcon(UIJanelaLogin.class.getResource("imagens/fundo_login.png")));
		lblFundoLogin.setBounds(0, 0, 400, 354);
		getContentPane().add(lblFundoLogin);
		getContentPane().addKeyListener(this);

		ok.addActionListener(this);
		exit.addActionListener(this);
		cadastrar.addActionListener(this);
		login.addKeyListener(this);
		senha.addKeyListener(this);

		this.addWindowListener(this);
		this.addKeyListener(this);
		repaint();
	}

	public void keyPressed(KeyEvent ke) {

		if (ke.getKeyCode() == KeyEvent.VK_ENTER)
			entrarAplicacao();
	}

	public void actionPerformed(ActionEvent a) {

		if (a.getSource() == ok) {
			entrarAplicacao();
		} else if (a.getSource() == exit) {
			int b = JOptionPane.showConfirmDialog(null, "Deseja Sair?", null, JOptionPane.YES_NO_OPTION);
			if (b == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		} else if (a.getSource() == cadastrar) {
			UIJanelaCadastrar cadastro = new UIJanelaCadastrar();
			cadastro.setVisible(true);
		}
	}

	private void entrarAplicacao() {

		try {
			String email = tlg.getText();
			String senha = new String(psenha.getPassword());

			// TODO PRECISA DESCOMENTAR AQUI - SERVIDOR AUTENTICAÇÃO
			if (!(email == null || email.equals("")) && !(senha == null || senha.equals(""))) {
				
				if (Utilitaria.verificarAutenticacaoUsuario(email, senha)) {
					ControlePainelPrincipalAnuncios.getInstance();
					this.dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Seu Email ou sua Senha estão incorretos");
				}
			} else {
				boolean nenhumCampoPreenchido = (email == null
						|| email.equals("") && (senha == null || senha.equals("")));

				if (nenhumCampoPreenchido)
					JOptionPane.showMessageDialog(null, "Ops! Você se esqueceu de informar seus dados");
				else {
					if (email == null || email.equals(""))
						JOptionPane.showMessageDialog(null, "Ops! Você se esqueceu de preencher seu \"Login\"");
					if (senha == null || senha.equals(""))
						JOptionPane.showMessageDialog(null, "Ops! Você se esqueceu de informar sua \"Senha\"");
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "        	    Desculpe pelo transtorno !\n" + "Estamos com problemas nos Servidores");
			e.printStackTrace();
		}
	}

	public void windowClosing(WindowEvent e) {
		int b = JOptionPane.showConfirmDialog(null, "Deseja Sair?", null, JOptionPane.YES_NO_OPTION);
		if (b == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	public static UIJanelaLogin getInstance() {

		if (instancia == null)
			return instancia = new UIJanelaLogin();

		return instancia;
	}
}
