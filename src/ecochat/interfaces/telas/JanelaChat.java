package ecochat.interfaces.telas;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ContainerListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ecochat.aplicacoes.cliente.ClienteUm;
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
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JLayeredPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings({ "serial", "unused" })
public class JanelaChat extends JanelaBase {

	private JFrame janelaChat;
	private static JanelaChat instancia;
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

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaChat.getInstance().janelaChat.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public JanelaChat() {
		initialize();
	}

	private void initialize() {

		janelaChat = new JFrame();
		janelaChat.setResizable(false);
		janelaChat.setAlwaysOnTop(true);
		janelaChat.setAutoRequestFocus(false);
		janelaChat.setBounds(100, 100, 350, 472);
		janelaChat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janelaChat.getContentPane().setLayout(null);

		lblEnviarArquivo = new JLabel(new ImageIcon(this.getClass().getResource("imagens\\anexo_icon_5.png")));
		lblEnviarArquivo.setBounds(10, 396, 45, 40);
		lblEnviarArquivo.addMouseListener(this);
		janelaChat.getContentPane().add(lblEnviarArquivo);

		lblEnviarMensagem = new JLabel(new ImageIcon(this.getClass().getResource("imagens\\enviar_icon_1.png")));
		lblEnviarMensagem.setBounds(284, 396, 60, 40);
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
		panelVisorChat.setLayout(new BoxLayout(panelVisorChat, BoxLayout.PAGE_AXIS));

		scrollPaneVisorChat = new JScrollPane(panelVisorChat);
		scrollPaneVisorChat.setViewportBorder(new EmptyBorder(0, 15, 0, 15));
		scrollPaneVisorChat.setBackground(Color.WHITE);
		scrollPaneVisorChat.setBounds(0, 0, 344, 385);

		janelaChat.getContentPane().add(scrollPaneVisorChat);

		exploradorArquivos = new JFileChooser();
		exploradorArquivos.setCurrentDirectory(new File("C:\\temp"));
		exploradorArquivos.setFileFilter(new FileNameExtensionFilter("PNG images", "png"));

		janelaChat.setVisible(true);
	}

	public static JanelaChat getInstance() {

		if (instancia == null)
			return instancia = new JanelaChat();

		return instancia;
	}

	public void adicionarMensagemDireita(String mensagem) {

		JLabel lblMensagem = null;

		lblMensagem = Utilitaria.quebrarLinhas(mensagem);

		lblMensagem.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMensagem.setForeground(Color.BLACK);
		lblMensagem.setOpaque(true);

		adicionarMensagemVisor(lblMensagem);
	}

	private void adicionarMensagemVisor(JLabel lblMensagem) {
		panelVisorChat.add(lblMensagem);
		panelVisorChat.revalidate();
		panelVisorChat.repaint();
		janelaChat.getContentPane().revalidate();
		janelaChat.getContentPane().repaint();
	}

	public void adicionarMensagemEsquerda(String mensagem) {

		JLabel lblMensagem = null;

		lblMensagem = Utilitaria.quebrarLinhas(mensagem);

		lblMensagem.setHorizontalAlignment(SwingConstants.LEFT);
		lblMensagem.setForeground(Color.BLACK);
		lblMensagem.setBackground(new Color(195, 223, 255));
		lblMensagem.setOpaque(true);

		adicionarMensagemVisor(lblMensagem);

	}

	@Override
	public void mouseClicked(MouseEvent me) {

		if (me.getSource() == lblEnviarMensagem) {
			enviarMensagem();
		} else if (me.getSource() == lblEnviarArquivo) {
			int valorRetornado = exploradorArquivos.showOpenDialog(this);
			if (valorRetornado == JFileChooser.APPROVE_OPTION) {

				arquivoEnvio = exploradorArquivos.getSelectedFile();

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

	private void enviarMensagem() {

		DadoCompartilhado dadoCompartilhado = new DadoCompartilhado();
		dadoCompartilhado.setMensagem(textAreaCampoEscritaChat.getText());
		dadoCompartilhado.setArquivo(arquivoEnvio);

		ClienteUm.getInstance();
		adicionarMensagemDireita(textAreaCampoEscritaChat.getText());

		// ClienteUm.escreverMensagemAoServidor(dadoCompartilhado);

		textAreaCampoEscritaChat.setText(null);
	}
}
