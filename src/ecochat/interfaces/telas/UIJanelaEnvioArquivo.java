package ecochat.interfaces.telas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ecochat.aplicacoes.telas.JanelaBase;
import ecochat.entidades.DadoAnuncio;
import ecochat.entidades.DadoCompartilhadoServidor;
import ecochat.utilitarios.ConstantesGerais;

@SuppressWarnings("serial")
public class UIJanelaEnvioArquivo extends JanelaBase {

	private JFrame frmCadastroDeAnuncio;
	private JTextField textTitulo;
	private JButton botaoPublicar;
	private JButton btnImagem;
	private static UIJanelaEnvioArquivo instancia;
	private final JLabel lblNewLabel_1 = new JLabel("New label");
	@SuppressWarnings("rawtypes")
	private JComboBox cbCategoria;
	private JFileChooser escolhaArquivo;
	private JTextArea textDescricao;

	public UIJanelaEnvioArquivo() {
		initialize();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() {
		frmCadastroDeAnuncio = this;
		this.setTitle("Cadastro de an\u00FAncio");
		this.setForeground(new Color(240, 255, 240));
		this.setBounds(100, 100, 330, 436);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);

		JLabel lblDescrio = new JLabel("Descri\u00E7\u00E3o");
		lblDescrio.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDescrio.setForeground(Color.WHITE);
		lblDescrio.setBounds(52, 270, 223, 14);
		this.getContentPane().add(lblDescrio);

		JLabel lblTtulo = new JLabel("T\u00EDtulo");
		lblTtulo.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTtulo.setForeground(Color.WHITE);
		lblTtulo.setBounds(52, 179, 223, 14);
		this.getContentPane().add(lblTtulo);
		textTitulo = new JTextField();
		textTitulo.setColumns(10);
		textTitulo.setBounds(52, 193, 223, 20);
		this.getContentPane().add(textTitulo);

		botaoPublicar = new JButton("PUBLICAR");
		botaoPublicar.setBounds(52, 346, 223, 30);
		botaoPublicar.addActionListener(new ActionPublicar());
		this.getContentPane().add(botaoPublicar);

		cbCategoria = new JComboBox();
		cbCategoria.setBounds(52, 239, 223, 20);
		cbCategoria.addItem("");
		cbCategoria.addItem("Celular");
		cbCategoria.addItem("Computador");
		cbCategoria.addItem("Rádio");
		cbCategoria.addItem("Televisor");
		cbCategoria.addItem("Outros");

		this.getContentPane().add(cbCategoria);
		JLabel lblCategoria = new JLabel("Categoria");
		lblCategoria.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblCategoria.setForeground(Color.WHITE);
		lblCategoria.setBounds(52, 224, 223, 14);
		this.getContentPane().add(lblCategoria);

		textDescricao = new JTextArea();
		textDescricao.setBounds(52, 286, 221, 40);
		textDescricao.setWrapStyleWord(true);
		textDescricao.setLineWrap(true);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(52, 286, 221, 40);
		scrollPane_1.setViewportView(textDescricao);
		this.getContentPane().add(scrollPane_1);
		
		JLabel lblInserirAnncio = new JLabel("                          Inserir An\u00FAncio");
		lblInserirAnncio.setForeground(Color.WHITE);
		lblInserirAnncio.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblInserirAnncio.setBounds(0, 0, 296, 30);
		this.getContentPane().add(lblInserirAnncio);
		lblNewLabel_1.setIcon(new ImageIcon(
				UIJanelaEnvioArquivo.class.getResource("/ecochat/interfaces/telas/imagens/barra_enviar_anuncio.png")));
		lblNewLabel_1.setBounds(0, 0, 458, 31);
		this.getContentPane().add(lblNewLabel_1);
		btnImagem = new JButton();

		btnImagem.setBackground(Color.WHITE);
		btnImagem.setForeground(Color.WHITE);
		btnImagem.setIcon(new ImageIcon(
				UIJanelaEnvioArquivo.class.getResource("/ecochat/interfaces/telas/imagens/inserir_anuncio.png")));
		btnImagem.setBounds(105, 53, 115, 115);
		escolhaArquivo = new JFileChooser();
		escolhaArquivo.setDialogTitle("Inserir Image");
		btnImagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				escolhaArquivo.showOpenDialog(btnImagem);
			}
		});
		this.getContentPane().add(btnImagem);

		ActionListener busca = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				procuraArquivo();
			}
		};

		btnImagem.addActionListener(busca);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(
				UIJanelaEnvioArquivo.class.getResource("/ecochat/interfaces/telas/imagens/Background1.jpg")));
		label.setBounds(0, 0, 324, 407);
		this.getContentPane().add(label);

		this.setVisible(true);
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
			Socket socketAutenticacao = null;

			DadoCompartilhadoServidor dcServidor = new DadoCompartilhadoServidor();
			DadoAnuncio anuncio = new DadoAnuncio();

			anuncio.setDescricao(textDescricao.getText());
			anuncio.setCategoria(cbCategoria.getSelectedItem().toString());
			anuncio.setImagem(btnImagem.getIcon());
			anuncio.setTitulo(textTitulo.getText());

			dcServidor.setAnuncio(anuncio);

			try {
				socketAutenticacao = new Socket(InetAddress.getByName(ConstantesGerais.IP_SERVIDOR_CENTRAL),
						ConstantesGerais.PORTA_SERVIDOR_CENTRAL,
						InetAddress.getByName(ConstantesGerais.IP_FIXO_ENVIO_ANUNCIO), 0);
				ObjectOutputStream fluxoSaidaAnuncio = new ObjectOutputStream(socketAutenticacao.getOutputStream());
				fluxoSaidaAnuncio.writeObject(dcServidor);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			frmCadastroDeAnuncio.dispose();
		}
	}
}
