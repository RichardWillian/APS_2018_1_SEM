package ecochat.interfaces.telas;

import java.awt.ComponentOrientation;
import java.awt.EventQueue;
import java.awt.event.ContainerListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ecochat.aplicacoes.cliente.ClienteUm;
import ecochat.aplicacoes.telas.JanelaBase;

public class JanelaChat extends JanelaBase{

	private JFrame janelaChat;
	private static JanelaChat instancia;
	private JLabel lblEnviarArquivo;
	private JLabel lblEnviarMensagem;
	private JTextArea textAreaVisorChat;
	private JTextArea textAreaCampoEscritaChat;
	private JScrollPane scrollBar;

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

		textAreaVisorChat = new JTextArea();
		textAreaVisorChat.setEnabled(false);
		textAreaVisorChat.setEditable(false);
		textAreaVisorChat.setLineWrap(true);
		textAreaVisorChat.setBounds(10, 11, 324, 374);
		janelaChat.getContentPane().add(textAreaVisorChat);

		textAreaCampoEscritaChat = new JTextArea();
		textAreaCampoEscritaChat.setToolTipText("");
		textAreaCampoEscritaChat.setWrapStyleWord(true);
		textAreaCampoEscritaChat.setLineWrap(true);
		textAreaCampoEscritaChat.setBounds(65, 396, 223, 40);
		textAreaCampoEscritaChat.addKeyListener(this);
		
		scrollBar = new JScrollPane(textAreaCampoEscritaChat);
		scrollBar.setSize(209, 36);
		scrollBar.setLocation(65, 396);
		scrollBar.setVisible(true);
		scrollBar.setEnabled(true);
		
		janelaChat.getContentPane().add(scrollBar);
	}
	
	public static JanelaChat getInstance(){
		
		if(instancia == null)
			return instancia = new JanelaChat();
		
		return instancia;
	}
	
	public void adicionarMensagemDireita(String mensagem){
		textAreaVisorChat.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		textAreaVisorChat.setText(mensagem);
	}
	
	public void keyPressed(KeyEvent ke){
		if(ke.getKeyCode() == KeyEvent.VK_ENTER){
			ClienteUm.getInstance().escreverMensagemAoServidor(textAreaCampoEscritaChat.getText());
			//textAreaCampoEscritaChat.setText("");
		}
	}
}
