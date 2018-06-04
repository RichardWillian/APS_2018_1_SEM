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

public class UIJanelaEnvioArquivo {

	private JFrame frmCadastroDeAnuncio;
	private JTextField textDescricao;
	private JTextField textTitulo;
	private JTextField textCategoria;
	private JTextField textLocalizacao;
	private static UIJanelaEnvioArquivo instancia;
	private final JLabel lblNewLabel_1 = new JLabel("New label");

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
		lblDescrio.setBounds(207, 205, 223, 14);
		frmCadastroDeAnuncio.getContentPane().add(lblDescrio);
		
		textDescricao = new JTextField();
		textDescricao.setBounds(207, 220, 223, 20);
		frmCadastroDeAnuncio.getContentPane().add(textDescricao);
		textDescricao.setColumns(10);
		
		JLabel lblTtulo = new JLabel("T\u00EDtulo");
		lblTtulo.setBounds(207, 160, 223, 14);
		frmCadastroDeAnuncio.getContentPane().add(lblTtulo);
		
		textTitulo = new JTextField();
		textTitulo.setColumns(10);
		textTitulo.setBounds(207, 175, 223, 20);
		frmCadastroDeAnuncio.getContentPane().add(textTitulo);
		
		JButton btnPublicar = new JButton("PUBLICAR");
		btnPublicar.setBounds(207, 345, 223, 30);
		frmCadastroDeAnuncio.getContentPane().add(btnPublicar);
		btnPublicar.addActionListener(new ActionPublicar());
		
		JLabel lblLocalizao = new JLabel("Localiza\u00E7\u00E3o");
		lblLocalizao.setBounds(207, 295, 223, 14);
		frmCadastroDeAnuncio.getContentPane().add(lblLocalizao);
		
		JLabel lblCategoria = new JLabel("Categoria");
		lblCategoria.setBounds(207, 250, 223, 14);
		frmCadastroDeAnuncio.getContentPane().add(lblCategoria);
		
		textCategoria = new JTextField();
		textCategoria.setColumns(10);
		textCategoria.setBounds(207, 265, 223, 20);
		frmCadastroDeAnuncio.getContentPane().add(textCategoria);
		
		textLocalizacao = new JTextField();
		textLocalizacao.setColumns(10);
		textLocalizacao.setBounds(207, 310, 223, 20);
		frmCadastroDeAnuncio.getContentPane().add(textLocalizacao);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(UIJanelaEnvioArquivo.class.getResource("/ecochat/interfaces/telas/imagens/Inserir.png")));
		lblNewLabel.setBounds(207, 49, 100, 100);
		frmCadastroDeAnuncio.getContentPane().add(lblNewLabel);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(UIJanelaEnvioArquivo.class.getResource("/ecochat/interfaces/telas/imagens/Inserir.png")));
		label_1.setBounds(331, 49, 100, 100);
		frmCadastroDeAnuncio.getContentPane().add(label_1);
		
		JLabel lblInserirAnncio = new JLabel("                                           Inserir An\u00FAncio");
		lblInserirAnncio.setForeground(Color.WHITE);
		lblInserirAnncio.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblInserirAnncio.setBounds(0, 0, 458, 30);
		frmCadastroDeAnuncio.getContentPane().add(lblInserirAnncio);
		lblNewLabel_1.setIcon(new ImageIcon(UIJanelaEnvioArquivo.class.getResource("/ecochat/interfaces/telas/imagens/Barra.png")));
		lblNewLabel_1.setBounds(0, 0, 458, 31);
		frmCadastroDeAnuncio.getContentPane().add(lblNewLabel_1);
		
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
			JLabel lblNewLabel = new JLabel("New label");
			lblNewLabel.setBounds(30, 30, 100, 80);
			lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Vitor\\Desktop\\produto.png"));
			telaAnuncio.getContentPane().add(lblNewLabel);

			JScrollPane scrollPane_1 = new JScrollPane();
			scrollPane_1.setBounds(133, 191, 350, 87);
			telaAnuncio.getContentPane().add(scrollPane_1);

			JTextArea descricao = new JTextArea();
			scrollPane_1.setViewportView(descricao);
			descricao.setWrapStyleWord(true);
			descricao.setFont(new Font("Arial", Font.PLAIN, 10));
			descricao.setEditable(false);
			descricao.setText(
					"Vendo pe\u00E7a eletronica com alguns wats e ela funciona em perfeito e nois que voa molecotinho da árabia estado troco por HD SSD. aquele abra\u00E7o amigos");
			descricao.setLineWrap(true);
			descricao.setAutoscrolls(true);
			
			telaAnuncio.add(scrollPane_1).setVisible(true);
			frmCadastroDeAnuncio.dispose();
		}
	}

}
