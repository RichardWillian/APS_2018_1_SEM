package ecochat.interfaces.telas;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.event.ContainerListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ecochat.aplicacoes.cliente.ClienteUm;
import ecochat.aplicacoes.telas.JanelaBase;
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
import javax.swing.JEditorPane;
import javax.swing.JLayeredPane;

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
		janelaChat.getContentPane().add(lblEnviarArquivo);

		lblEnviarMensagem = new JLabel(new ImageIcon(this.getClass().getResource("imagens\\enviar_icon_1.png")));
		lblEnviarMensagem.setBounds(284, 396, 60, 40);
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
		panelVisorChat.setBounds(0, 0, 344, 385);
		panelVisorChat.setLayout(new BoxLayout(panelVisorChat, BoxLayout.PAGE_AXIS));

		scrollPaneVisorChat = new JScrollPane(panelVisorChat);
		scrollPaneVisorChat.setBackground(new Color(200, 200, 200));
		scrollPaneVisorChat.setVisible(true);
		scrollPaneVisorChat.setEnabled(true);
		scrollPaneVisorChat.setBounds(10, 11, 324, 374);

		janelaChat.getContentPane().add(scrollPaneVisorChat);

		janelaChat.setVisible(true);
	}

	public static JanelaChat getInstance() {

		if (instancia == null)
			return instancia = new JanelaChat();

		return instancia;
	}

	public void adicionarMensagemDireita(String mensagem) {

		JLabel lblMensagem = null;

		lblMensagem = quebrarLinhas(mensagem);

		lblMensagem.setHorizontalAlignment(SwingConstants.RIGHT);
		//ultimoYLabel += tamanhoPadraoLabel;

		lblMensagem.setForeground(Color.BLACK);
		lblMensagem.setOpaque(true);

		adicionarMensagemVisor(lblMensagem);
	}

	private void adicionarMensagemVisor(JLabel lblMensagem) {
		panelVisorChat.add(lblMensagem);
		panelVisorChat.add(Box.createRigidArea(new Dimension(0,1)));
		panelVisorChat.revalidate();
		panelVisorChat.repaint();
		janelaChat.getContentPane().revalidate();
		janelaChat.getContentPane().repaint();
	}

	private JLabel quebrarLinhas(String mensagem) {
		JLabel lblMensagem;
		if (mensagem.length() > 40) {

			StringBuilder stringFormatada = new StringBuilder();

			char[] caractesMensagem = mensagem.toCharArray();
			StringBuilder caracteresAteAqui = new StringBuilder();
			boolean concatenarCaracter = false;

			for (int i = 0; i < mensagem.length(); i++) {

				caracteresAteAqui.append(caractesMensagem[i]);

				if (i != 0 && i % 30 == 0) {

					char caracterCorrenteUm = caractesMensagem[i];
					if (caracterCorrenteUm != ' ') {

						for (int j = i; j < mensagem.length(); j++) {

							if (concatenarCaracter)
								caracteresAteAqui.append(caractesMensagem[j]);

							char caracterCorrenteDois = caractesMensagem[j];
							if (caracterCorrenteDois == ' ') {

								stringFormatada.append(caracteresAteAqui + "<br>");
								caracteresAteAqui = new StringBuilder();
								i = j - 1;
								concatenarCaracter = false;
								break;
							} else {
								concatenarCaracter = true;
								if (j % 11 == 0) {
									stringFormatada.append(caracteresAteAqui + "<br>");
									caracteresAteAqui = new StringBuilder();
									break;
								}
							}
						}
					} else {

						stringFormatada.append(caracteresAteAqui + "<br>");
						caracteresAteAqui = new StringBuilder();

					}
				}
			}

			lblMensagem = new JLabel("<html><p>" + stringFormatada + "</html>");

		} else
			lblMensagem = new JLabel("<html><p>" + mensagem + "</html>");
		return lblMensagem;
	}

	public void adicionarMensagemEsquerda(String mensagem) {

		JLabel lblMensagem = null;

		lblMensagem = quebrarLinhas(mensagem);

		lblMensagem.setHorizontalAlignment(SwingConstants.LEFT);

		lblMensagem.setForeground(Color.BLACK);
		lblMensagem.setOpaque(true);

		adicionarMensagemVisor(lblMensagem);
		
	}

	public void keyPressed(KeyEvent ke) {
		if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
			ClienteUm.getInstance();
			adicionarMensagemDireita(textAreaCampoEscritaChat.getText());
			ClienteUm.escreverMensagemAoServidor(textAreaCampoEscritaChat.getText());
			textAreaCampoEscritaChat.setText(null);
		}
	}
}
