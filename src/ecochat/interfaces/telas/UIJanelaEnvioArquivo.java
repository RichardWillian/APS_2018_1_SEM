package ecochat.interfaces.telas;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.SystemColor;
import javax.swing.JComboBox;

public class UIJanelaEnvioArquivo {

	private JFrame frmCadastroDeAnuncio;
	private JTextField textTitulo;
	private static UIJanelaEnvioArquivo instancia;
	private final JLabel lblNewLabel_1 = new JLabel("New label");
	private JTextArea textArea_1;
	public UIJanelaEnvioArquivo() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCadastroDeAnuncio = new JFrame();
		frmCadastroDeAnuncio.setTitle("Cadastro de an\u00FAncio");
		frmCadastroDeAnuncio.setForeground(new Color(240, 255, 240));
		frmCadastroDeAnuncio.setBounds(100, 100, 474, 430);
		frmCadastroDeAnuncio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCadastroDeAnuncio.getContentPane().setLayout(null);
		
		JLabel lblDescrio = new JLabel("Descri\u00E7\u00E3o");
		lblDescrio.setBounds(207, 270, 223, 14);
		frmCadastroDeAnuncio.getContentPane().add(lblDescrio);
		
		JLabel lblTtulo = new JLabel("T\u00EDtulo");
		lblTtulo.setBounds(207, 175, 223, 14);
		frmCadastroDeAnuncio.getContentPane().add(lblTtulo);
		
		textTitulo = new JTextField();
		textTitulo.setColumns(10);
		textTitulo.setBounds(207, 190, 223, 20);
		frmCadastroDeAnuncio.getContentPane().add(textTitulo);
		
		JButton botaoPublicar = new JButton("PUBLICAR");
		botaoPublicar.setBounds(207, 345, 223, 30);
		frmCadastroDeAnuncio.getContentPane().add(botaoPublicar);
		botaoPublicar.addActionListener(new ActionPublicar());
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(207, 240, 223, 20);
		comboBox.addItem("");	
		comboBox.addItem("Celular");				
		comboBox.addItem("Computador");				
		comboBox.addItem("Rádio");				
		comboBox.addItem("Televisor");				
		comboBox.addItem("Outros");				

		
		frmCadastroDeAnuncio.getContentPane().add(comboBox);
		
		JLabel lblCategoria = new JLabel("Categoria");
		lblCategoria.setBounds(207, 225, 223, 14);
		frmCadastroDeAnuncio.getContentPane().add(lblCategoria);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(207, 285, 221, 40);
		frmCadastroDeAnuncio.getContentPane().add(scrollPane_1);
		
		textArea_1 = new JTextArea();
		textArea_1.setWrapStyleWord(true);
		textArea_1.setLineWrap(true);
		scrollPane_1.setViewportView(textArea_1);
		
		JLabel lblInserirAnncio = new JLabel("                                           Inserir An\u00FAncio");
		lblInserirAnncio.setForeground(Color.WHITE);
		lblInserirAnncio.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblInserirAnncio.setBounds(0, 0, 458, 30);
		frmCadastroDeAnuncio.getContentPane().add(lblInserirAnncio);
		lblNewLabel_1.setIcon(new ImageIcon(UIJanelaEnvioArquivo.class.getResource("/ecochat/interfaces/telas/imagens/Barra.png")));
		lblNewLabel_1.setBounds(0, 0, 458, 31);
		frmCadastroDeAnuncio.getContentPane().add(lblNewLabel_1);
		
		JButton btnImagem = new JButton("");
		btnImagem.setBackground(Color.WHITE);
		btnImagem.setForeground(Color.WHITE);
		btnImagem.setIcon(new ImageIcon(UIJanelaEnvioArquivo.class.getResource("/ecochat/interfaces/telas/imagens/Inserir.png")));
		btnImagem.setBounds(270, 55, 115, 115);
		frmCadastroDeAnuncio.getContentPane().add(btnImagem);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(UIJanelaEnvioArquivo.class.getResource("/ecochat/interfaces/telas/imagens/background3.jpg")));
		label.setBounds(0, 0, 458, 391);
		frmCadastroDeAnuncio.getContentPane().add(label);
		
		frmCadastroDeAnuncio.setVisible(true);
	}
	
	
	public static UIJanelaEnvioArquivo getInstance() {
		if (instancia == null) {
			return instancia = new UIJanelaEnvioArquivo();
		}
		return instancia;
	}
	
	private class ActionPublicar implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JInternalFrame telaAnuncio = UIJanelaPrincipal.getInstance().getInternalFrame();
			JLabel foto = new JLabel("New label");
			foto.setBounds(100, 100, 100, 80);
			foto.setIcon(new ImageIcon("C:\\Users\\Vitor\\Desktop\\produto.png"));
			telaAnuncio.getContentPane().add(foto);

			JScrollPane scrollPane_1 = new JScrollPane();
			scrollPane_1.setBounds(150, 150, 400, 100);
			telaAnuncio.getContentPane().add(scrollPane_1);

			JTextArea descricao = new JTextArea();
			scrollPane_1.setViewportView(descricao);
			descricao.setWrapStyleWord(true);
			descricao.setFont(new Font("Arial", Font.PLAIN, 10));
			descricao.setEditable(false);
			descricao.setText(textArea_1.getText());
			descricao.setLineWrap(true);
			descricao.setAutoscrolls(true);
			
			telaAnuncio.getContentPane().add(scrollPane_1).setVisible(true);
			frmCadastroDeAnuncio.dispose();
		}
	}
}
