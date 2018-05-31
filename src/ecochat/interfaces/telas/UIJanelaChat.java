package ecochat.interfaces.telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ContainerListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ecochat.aplicacoes.cliente.Aplicacao;
import ecochat.aplicacoes.servidor.ServidorChatAplicacao;
import ecochat.aplicacoes.telas.JanelaBase;
import ecochat.entidades.DadoCompartilhado;
import ecochat.utilitarios.Utilitaria;

import javax.swing.JScrollBar;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.TabSet;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JLayeredPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings({ "serial", "unused" })
public class UIJanelaChat extends JanelaBase {

	private JFrame janelaChat;
	private static UIJanelaChat instancia;
	private JLabel lblEnviarArquivo;
	private JLabel lblEnviarMensagem;
	private JTextArea textAreaCampoEscritaChat;
	private JScrollPane scrollPaneCampoEscritaChat;

	private int tamanhoPadraoLabel = 30;
	private int ultimoYLabel;

	private JScrollPane scrollPaneVisorChat;
	private JPanel panelVisorChat;
	private JFileChooser exploradorArquivos;
	private File arquivoEnvio = null;
	private JLabel lblArquivo;
	private JLabel lblLoading;

	public UIJanelaChat() {
		initialize();
	}

	private void initialize() {

		janelaChat = new JFrame();
		janelaChat.setAlwaysOnTop(true);
		janelaChat.setResizable(false);
		janelaChat.setAutoRequestFocus(false);
		janelaChat.setBounds(100, 100, 350, 472);
		janelaChat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janelaChat.getContentPane().setLayout(null);

		lblEnviarArquivo = criarLabelComImagem(this.getClass().getResource("imagens\\anexo_icon.png"));
		lblEnviarArquivo.setBounds(10, 392, 45, 40);
		lblEnviarArquivo.addMouseListener(this);
		janelaChat.getContentPane().add(lblEnviarArquivo);

		lblEnviarMensagem = criarLabelComImagem(this.getClass().getResource("imagens\\botao_enviar.gif"));
		lblEnviarMensagem.setBounds(280, 390, 60, 40);
		lblEnviarMensagem.addMouseListener(this);
		janelaChat.getContentPane().add(lblEnviarMensagem);

		textAreaCampoEscritaChat = new JTextArea();
		textAreaCampoEscritaChat.setWrapStyleWord(true);
		textAreaCampoEscritaChat.setVisible(true);
		textAreaCampoEscritaChat.setEnabled(true);
		textAreaCampoEscritaChat.setLineWrap(true);
		textAreaCampoEscritaChat.setBounds(65, 396, 223, 40);
		textAreaCampoEscritaChat.addKeyListener(this);

		scrollPaneCampoEscritaChat = new JScrollPane(textAreaCampoEscritaChat);
		scrollPaneCampoEscritaChat.setSize(209, 36);
		scrollPaneCampoEscritaChat.setLocation(65, 396);

		janelaChat.getContentPane().add(scrollPaneCampoEscritaChat);

		panelVisorChat = new JPanel();
		panelVisorChat.setBackground(Color.WHITE);
		panelVisorChat.setBorder(new EmptyBorder(0, 10, 0, 0));
		panelVisorChat.setBounds(0, 0, 344, 385);
		panelVisorChat.setEnabled(false);
		panelVisorChat.setLayout(new BoxLayout(panelVisorChat, BoxLayout.PAGE_AXIS));

		scrollPaneVisorChat = new JScrollPane(panelVisorChat);
		scrollPaneVisorChat.setViewportBorder(new EmptyBorder(2, 10, 2, 10));
		scrollPaneVisorChat.setEnabled(false);
		scrollPaneVisorChat.setBounds(0, 0, 344, 385);

		janelaChat.getContentPane().add(scrollPaneVisorChat);

		exploradorArquivos = new JFileChooser();
		exploradorArquivos.setCurrentDirectory(new File("C:\\Users\\richard.divino\\Desktop\\Cliente"));

		janelaChat.setVisible(true);
	}

	public static UIJanelaChat getInstance() {

		if (instancia == null)
			return instancia = new UIJanelaChat();

		return instancia;
	}

	private void adicionarMensagemEnviada(String mensagem) {

		JLabel lblMensagem = null;

		lblMensagem = Utilitaria.quebrarLinhas(mensagem);

		lblMensagem.setHorizontalAlignment(SwingConstants.RIGHT);

		lblMensagem.setOpaque(true);
		lblMensagem.setBackground(new Color(198, 255, 215));
		lblMensagem.setForeground(Color.BLACK);

		adicionarMensagemVisor(lblMensagem);
	}

	private void adicionarMensagemRecebida(String mensagem) {

		JLabel lblMensagem = null;

		lblMensagem = Utilitaria.quebrarLinhas(mensagem);
		lblMensagem.setOpaque(true);
		lblMensagem.setBackground(new Color(249, 255, 198));
		lblMensagem.setForeground(Color.BLACK);

		adicionarMensagemVisor(lblMensagem);
	}

	private void enviarMensagem() {

		DadoCompartilhado dadoCompartilhado = new DadoCompartilhado();

		if (textAreaCampoEscritaChat.getText() != null && textAreaCampoEscritaChat.getText() != "") {
			dadoCompartilhado.setMensagem(textAreaCampoEscritaChat.getText());
			adicionarMensagemEnviada(textAreaCampoEscritaChat.getText());
			textAreaCampoEscritaChat.setText(null);
		}

		if (arquivoEnvio != null) {
			dadoCompartilhado.setArquivo(arquivoEnvio);
			adicionarAnimacaoArquivo();
		}

		ServidorChatAplicacao.getInstance().enviarMensagemAoServidor(dadoCompartilhado);
	}

	public void receberMensagem(String mensagemRecebida) {
		adicionarMensagemRecebida(mensagemRecebida);
	}

	protected void repintarTela() {
		panelVisorChat.revalidate();
		panelVisorChat.repaint();
		janelaChat.getContentPane().revalidate();
		janelaChat.getContentPane().repaint();
	}

	public void adicionarAnimacaoArquivo() {

		lblLoading = criarLabelComImagem(this.getClass().getResource("imagens/loading_icon.gif"));
		lblLoading.setLocation(100, 150);

		panelVisorChat.add(lblLoading);
	}

	public void trocarLoadingPorImagemArquivo(String mensagem, File arquivo) {

		panelVisorChat.remove(lblLoading);

		lblArquivo = new JLabel();
		lblArquivo.setSize(100, 60);

		BufferedImage imagem = null;
		boolean isImagem = Utilitaria.identificarTipoArquivo(arquivo);

		if (isImagem) {

			try {
				imagem = ImageIO.read(arquivo);
			} catch (IOException io) {
				System.out.println(io.getMessage());
			}

			Image imagemDinamizada = imagem.getScaledInstance(lblArquivo.getWidth(), lblArquivo.getHeight(),
					Image.SCALE_SMOOTH);

			lblArquivo.setIcon(new ImageIcon(imagemDinamizada));
			lblArquivo.setLocation(new Point(110, 100));
			lblArquivo.setOpaque(true);
			lblArquivo.setBackground(new Color(210, 253, 255, Color.TRANSLUCENT));
			lblArquivo.setText(mensagem);

			panelVisorChat.add(lblArquivo);
			arquivoEnvio = null;
			repintarTela();
		}
	}

	private void adicionarMensagemVisor(JLabel lblMensagem) {
		panelVisorChat.add(lblMensagem);
		repintarTela();
	}

	private JLabel criarLabelComImagem(URL caminho) {

		Image imagem = null;
		imagem = Toolkit.getDefaultToolkit().createImage(caminho);

		return new JLabel(new ImageIcon(imagem));
	}

	public void mouseEntered(MouseEvent me) {

		if (me.getSource() == lblEnviarMensagem) {
			lblEnviarMensagem.setIcon(new ImageIcon(this.getClass().getResource("imagens\\gif_botao_enviar.gif")));
			repintarTela();
		}
	}

	public void mouseExited(MouseEvent me) {

		if (me.getSource() == lblEnviarMensagem) {
			lblEnviarMensagem.setIcon(new ImageIcon(this.getClass().getResource("imagens\\botao_enviar.gif")));
			repintarTela();
		}
	}

	public void mouseClicked(MouseEvent me) {

		if (me.getSource() == lblEnviarMensagem) {
			if (textAreaCampoEscritaChat.getText() != "" || arquivoEnvio != null)
				enviarMensagem();
		} else if (me.getSource() == lblEnviarArquivo) {
			int valorRetornado = exploradorArquivos.showOpenDialog(this);
			if (valorRetornado == JFileChooser.APPROVE_OPTION) {
				arquivoEnvio = new File(exploradorArquivos.getSelectedFile(), "");
			} else {
				System.out.println("Nenhum arquivo selecionado");
			}
		}
	}

	public void keyPressed(KeyEvent ke) {
		if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
			enviarMensagem();
		}
	}
}
