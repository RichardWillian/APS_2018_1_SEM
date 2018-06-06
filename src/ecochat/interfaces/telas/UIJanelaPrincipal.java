package ecochat.interfaces.telas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

import ecochat.aplicacoes.telas.JanelaBase;
import ecochat.entidades.DadoAnuncio;
import ecohat.aplicacoes.servidor.controle.ControleChatAplicacao;

@SuppressWarnings("serial")
public class UIJanelaPrincipal extends JanelaBase {

	private JFrame FrmEcOLX;
	private JPanel panel_1;
	private JPanel panel;
	private static UIJanelaPrincipal instancia;
	private String ipChat;
	private List<JButton> listaUsuariosConectados;
	private JInternalFrame internalFrame;

	public UIJanelaPrincipal() {
		FrmEcOLX = new JFrame();

		FrmEcOLX.getContentPane().setBackground(Color.WHITE);
		FrmEcOLX.setBounds(100, 100, 579, 446);
		FrmEcOLX.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FrmEcOLX.getContentPane().setLayout(null);

		JLabel imagemUsuario = new JLabel("");
		imagemUsuario.setBounds(46, 11, 55, 64);
		imagemUsuario.setIcon(new ImageIcon("C:\\Users\\Vitor\\Desktop\\testeaps.png"));
		FrmEcOLX.getContentPane().add(imagemUsuario);

		JTextPane nomeUsuario = new JTextPane();
		nomeUsuario.setBounds(42, 76, 69, 14);
		nomeUsuario.setEnabled(false);
		nomeUsuario.setEditable(false);
		nomeUsuario.setText("Vitor Santos");
		FrmEcOLX.getContentPane().add(nomeUsuario);

		setInternalFrame(new JInternalFrame("ANUNCIOS"));
		getInternalFrame().setBounds(154, 42, 409, 354);
		FrmEcOLX.getContentPane().add(getInternalFrame());
		FrmEcOLX.setVisible(true);
		getInternalFrame().setResizable(false);
		internalFrame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));

		JScrollPane scrollPane_1 = new JScrollPane();
		internalFrame.getContentPane().add(scrollPane_1);

		setPanel(new JPanel());
		scrollPane_1.setViewportView(getPanel());
		getPanel().setLayout(new GridLayout(6, 1, 0, 0));

		getInternalFrame().setVisible(true);

		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(10, 101, 124, 20);
		comboBox.addItem("Online");
		comboBox.addItem("Offline");
		comboBox.addItem("Ausente");
		comboBox.addItem("Ocupado");

		FrmEcOLX.getContentPane().add(comboBox);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 134, 124, 262);
		FrmEcOLX.getContentPane().add(scrollPane);

		panel_1 = new JPanel();
		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(new GridLayout(10, 1, 0, 0));

		JButton cadastrarAnuncio = new JButton("Cadastrar An\u00FAncio");
		cadastrarAnuncio.setForeground(Color.DARK_GRAY);
		cadastrarAnuncio.setBackground(Color.GREEN);
		cadastrarAnuncio.setBounds(154, 8, 152, 23);
		cadastrarAnuncio.addActionListener(new ActionCadastrar());
		FrmEcOLX.getContentPane().add(cadastrarAnuncio);
		// ControleChatAplicacao.lerMensagemServidor();
		listaUsuariosConectados = new ArrayList<JButton>();
	}

	int k = 6;
	int j = 10;

	public void adicionarUsuariosOnline(final String emailConectado) {
		new Thread() {
			public void run() {
				j++;
				final JButton btnUsuarioConectado = new JButton(emailConectado);
				btnUsuarioConectado.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						setIpChat(emailConectado);
						ControleChatAplicacao.getInstance();
						UIJanelaChat.setIdJanela(emailConectado);
						if (btnUsuarioConectado.getBackground() == Color.CYAN) {
							btnUsuarioConectado.setBackground(null);
						}
					}
				});

				listaUsuariosConectados.add(btnUsuarioConectado);
				panel_1.add(btnUsuarioConectado);
				panel_1.setLayout(new GridLayout(j, 1, 0, 0));
				repintarTela();
			}
		}.start();
	}

	private void repintarTela() {

		FrmEcOLX.revalidate();
		FrmEcOLX.repaint();
		panel_1.revalidate();
		panel_1.repaint();
	}

	public static UIJanelaPrincipal getInstance() {
		if (instancia == null) {
			return instancia = new UIJanelaPrincipal();
		}
		return instancia;
	}

	public String getIpChat() {
		return ipChat;
	}

	public void setIpChat(String ipChat) {
		this.ipChat = ipChat;
	}

	public JInternalFrame getInternalFrame() {
		return internalFrame;
	}

	public void setInternalFrame(JInternalFrame internalFrame) {
		this.internalFrame = internalFrame;
	}

	private class ActionCadastrar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new UIJanelaEnvioArquivo();
		}

	}
	
	public void adicionaPainel(DadoAnuncio anuncio) {
			k++;
			JPanel painel = new JPanel();
			JTextArea descricao = new JTextArea(anuncio.getDescricao());
			JScrollPane scrollPanel = new JScrollPane();
			JLabel titulo = new JLabel(anuncio.getTitulo() + " ("+anuncio.getCategoria()+")");
			JLabel imagem = new JLabel();
			
			imagem.setIcon(anuncio.getImagem());
			
			scrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPanel.setPreferredSize(new Dimension(5,50));
			scrollPanel.setViewportView(descricao);
			scrollPanel.setColumnHeaderView(titulo);

			descricao.setEditable(false);
			descricao.setEnabled(true);
			descricao.setWrapStyleWord(true);
			descricao.setLineWrap(true);
			
			painel.setPreferredSize(new Dimension(70, 50));
			painel.setLayout(new GridLayout(1, 1, 0, 0));
			painel.add(imagem);
			painel.add(scrollPanel);
			
			getPanel().setLayout(new GridLayout(k, 1, 0, 5));
			getPanel().add( painel );
			repintarTela();
	}

	public void notificarUsuario(String ipSocketEnviouMensagem) {
		if (UIJanelaChat.getInstance().getFocusableWindowState())
			for (JButton botaoLista : listaUsuariosConectados) {
				String ipUsuarioRemetente = botaoLista.getText();
				if (ipUsuarioRemetente.equals(ipSocketEnviouMensagem)) {
					botaoLista.setBackground(Color.CYAN);
					repintarTela();
				}
			}
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}
}