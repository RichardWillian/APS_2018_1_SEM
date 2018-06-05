package ecochat.interfaces.telas;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

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
import javafx.stage.FileChooser;
import sun.security.action.OpenFileInputStreamAction;

import java.awt.SystemColor;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

public class UIJanelaEnvioArquivo {

	private JFrame frmCadastroDeAnuncio;
	private JTextField textTitulo;
	private static UIJanelaEnvioArquivo instancia;
	private final JLabel lblNewLabel_1 = new JLabel("New label");
	
	private JTextArea descricao;
	private JComboBox cbCategoria;
	private JButton btnImagem;
	private JFileChooser escolhaArquivo;
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
		
		JLabel lblDescricao = new JLabel("Descri\u00E7\u00E3o");
		lblDescricao.setBounds(207, 270, 223, 14);
		frmCadastroDeAnuncio.getContentPane().add(lblDescricao);
		
		JLabel lblTitulo = new JLabel("T\u00EDtulo");
		lblTitulo.setBounds(207, 175, 223, 14);
		frmCadastroDeAnuncio.getContentPane().add(lblTitulo);
		
		textTitulo = new JTextField();
		textTitulo.setColumns(10);
		textTitulo.setBounds(207, 190, 223, 20);
		frmCadastroDeAnuncio.getContentPane().add(textTitulo);
		
		JButton botaoPublicar = new JButton("PUBLICAR");
		botaoPublicar.setBounds(207, 345, 223, 30);
		frmCadastroDeAnuncio.getContentPane().add(botaoPublicar);
		botaoPublicar.addActionListener(new ActionPublicar());
		
		cbCategoria = new JComboBox();
		cbCategoria.setBounds(207, 240, 223, 20);
		cbCategoria.addItem("");	
		cbCategoria.addItem("Celular");				
		cbCategoria.addItem("Computador");				
		cbCategoria.addItem("Rádio");				
		cbCategoria.addItem("Televisor");				
		cbCategoria.addItem("Outros");				

		
		frmCadastroDeAnuncio.getContentPane().add(cbCategoria);
		
		JLabel lblCategoria = new JLabel("Categoria");
		lblCategoria.setBounds(207, 225, 223, 14);
		frmCadastroDeAnuncio.getContentPane().add(lblCategoria);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(207, 285, 221, 40);
		frmCadastroDeAnuncio.getContentPane().add(scrollPane_1);
		
		descricao = new JTextArea();
		descricao.setWrapStyleWord(true);
		descricao.setLineWrap(true);
		scrollPane_1.setViewportView(descricao);
		
		JLabel lblInserirAnuncio = new JLabel("                                           Inserir An\u00FAncio");
		lblInserirAnuncio.setForeground(Color.WHITE);
		lblInserirAnuncio.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblInserirAnuncio.setBounds(0, 0, 458, 30);
		frmCadastroDeAnuncio.getContentPane().add(lblInserirAnuncio);
		lblNewLabel_1.setIcon(new ImageIcon(UIJanelaEnvioArquivo.class.getResource("/ecochat/interfaces/telas/imagens/Barra.png")));
		lblNewLabel_1.setBounds(0, 0, 458, 31);
		frmCadastroDeAnuncio.getContentPane().add(lblNewLabel_1);
		
		btnImagem = new JButton("");
		btnImagem.setBackground(Color.WHITE);
		btnImagem.setForeground(Color.WHITE);
		btnImagem.setIcon(new ImageIcon(UIJanelaEnvioArquivo.class.getResource("/ecochat/interfaces/telas/imagens/Inserir.png")));
		btnImagem.setBounds(270, 55, 115, 115);
		escolhaArquivo = new JFileChooser();
		escolhaArquivo.setDialogTitle("Inserir Image");
		btnImagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				escolhaArquivo.showOpenDialog(btnImagem);
			}
		});
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
			DadoAnuncio anuncio = new DadoAnuncio();
			DadoCompartilhadoServidor dcServidor = new DadoCompartilhadoServidor();
			List<DadoAnuncio> listaDeAnuncios = new ArrayList<>();
			
			anuncio.setDescricao(descricao.getText());
			anuncio.setCategoria(cbCategoria.getSelectedItem().toString());
			anuncio.setImagem(escolhaArquivo.getSelectedFile());
			anuncio.setTitulo(textTitulo.getText());
			
			listaDeAnuncios.add(anuncio);
			dcServidor.setAnuncio(anuncio);
			UIJanelaPrincipal.getInstance().adicionaPainel(dcServidor.getAnuncio());
		
		}
	}
}
