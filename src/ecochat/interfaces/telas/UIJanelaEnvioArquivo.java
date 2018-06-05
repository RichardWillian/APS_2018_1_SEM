package ecochat.interfaces.telas;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
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

import ecochat.aplicacoes.servidor.ServidorCentral;
import ecochat.entidades.DadoAnuncio;
import ecochat.entidades.DadoCompartilhadoServidor;
import ecohat.aplicacoes.servidor.controle.ControlePainelPrincipalAnuncios;
import sun.security.action.OpenFileInputStreamAction;

import java.awt.SystemColor;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

public class UIJanelaEnvioArquivo {

	private JFrame frmCadastroDeAnuncio;
	private JTextField textTitulo;
	private JButton botaoPublicar;
	private JButton btnImagem;
	private static UIJanelaEnvioArquivo instancia;
	private final JLabel lblNewLabel_1 = new JLabel("New label");
	private JTextArea descricao;
	private JComboBox cbCategoria;
	private JFileChooser escolhaArquivo;
	private JTextArea textArea_1;
	public UIJanelaEnvioArquivo() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() {
		frmCadastroDeAnuncio = new JFrame();
		frmCadastroDeAnuncio.setTitle("Cadastro de an\u00FAncio");
		frmCadastroDeAnuncio.setForeground(new Color(240, 255, 240));
		frmCadastroDeAnuncio.setBounds(100, 100, 474, 430);
		frmCadastroDeAnuncio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCadastroDeAnuncio.getContentPane().setLayout(null);


		JLabel lblDescrio = new JLabel("Descri\u00E7\u00E3o");
		lblDescrio.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDescrio.setForeground(Color.WHITE);
		lblDescrio.setBounds(207, 270, 223, 14);
		frmCadastroDeAnuncio.getContentPane().add(lblDescrio);

		JLabel lblTtulo = new JLabel("T\u00EDtulo");
		lblTtulo.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTtulo.setForeground(Color.WHITE);
		lblTtulo.setBounds(207, 175, 223, 14);
		frmCadastroDeAnuncio.getContentPane().add(lblTtulo);
		textTitulo = new JTextField();
		textTitulo.setColumns(10);
		textTitulo.setBounds(207, 190, 223, 20);
		frmCadastroDeAnuncio.getContentPane().add(textTitulo);

		botaoPublicar = new JButton("PUBLICAR");
		botaoPublicar.setBounds(207, 345, 223, 30);
<<<<<<< HEAD
		botaoPublicar.addActionListener(new ActionPublicar());
=======
>>>>>>> 68d2d30b9732352a68ba2bddc3181f4f2e8befca
		frmCadastroDeAnuncio.getContentPane().add(botaoPublicar);

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
		lblCategoria.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblCategoria.setForeground(Color.WHITE);
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
		lblNewLabel_1.setIcon(
				new ImageIcon(UIJanelaEnvioArquivo.class.getResource("/ecochat/interfaces/telas/imagens/Barra.png")));
		lblNewLabel_1.setBounds(0, 0, 458, 31);
		frmCadastroDeAnuncio.getContentPane().add(lblNewLabel_1);
<<<<<<< HEAD
		btnImagem = new JButton();
=======
>>>>>>> 68d2d30b9732352a68ba2bddc3181f4f2e8befca
		btnImagem.setBackground(Color.WHITE);
		btnImagem.setForeground(Color.WHITE);
		btnImagem.setIcon(
				new ImageIcon(UIJanelaEnvioArquivo.class.getResource("/ecochat/interfaces/telas/imagens/Inserir.png")));
		btnImagem.setBounds(270, 55, 115, 115);
		escolhaArquivo = new JFileChooser();
		escolhaArquivo.setDialogTitle("Inserir Image");
		btnImagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				escolhaArquivo.showOpenDialog(btnImagem);
			}
		});
		frmCadastroDeAnuncio.getContentPane().add(btnImagem);

		ActionListener busca = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				procuraArquivo();
			}
		};

		btnImagem.addActionListener(busca);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(
				UIJanelaEnvioArquivo.class.getResource("/ecochat/interfaces/telas/imagens/Background1.jpg")));
		label.setBounds(0, 0, 458, 391);
		frmCadastroDeAnuncio.getContentPane().add(label);

		frmCadastroDeAnuncio.setVisible(true);
	}

	public void procuraArquivo() {
		String diretorioBase = System.getProperty("User.home") + "/Desktop";
		File dir = new File(diretorioBase);

		String caminhoArquivo = "";

		JFileChooser choose = new JFileChooser();
		choose.setCurrentDirectory(dir);
		choose.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int retorno = choose.showOpenDialog(null);
		if (retorno == JFileChooser.APPROVE_OPTION) {
			caminhoArquivo = choose.getSelectedFile().getAbsolutePath();
			BufferedImage imagem = null;
			try {
				imagem = ImageIO.read(new File(caminhoArquivo));
			} catch (IOException e) {
				e.printStackTrace();
			}
			Image imagemDinamizada = imagem.getScaledInstance(btnImagem.getWidth(), btnImagem.getHeight(),
					Image.SCALE_SMOOTH);
			btnImagem.setIcon(new ImageIcon(imagemDinamizada));
			btnImagem.revalidate();
			btnImagem.repaint();						
		}
	}

	public static UIJanelaEnvioArquivo getInstance() {
		if (instancia == null) {
			return instancia = new UIJanelaEnvioArquivo();
		}
		return instancia;
	}

	private class ActionPublicar implements ActionListener {
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
