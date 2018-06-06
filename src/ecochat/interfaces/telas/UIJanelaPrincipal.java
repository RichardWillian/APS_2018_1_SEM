package ecochat.interfaces.telas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
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

import ecochat.aplicacoes.servidor.controle.ControleChatAplicacao;
import ecochat.aplicacoes.telas.JanelaBase;
import ecochat.entidades.DadoAnuncio;
import ecochat.utilitarios.Utilitaria;

@SuppressWarnings("serial")
public class UIJanelaPrincipal extends JanelaBase {

	private JPanel panel_1;
	private JPanel panel;
	private static UIJanelaPrincipal instancia;
	private String ipChat;
	private List<JButton> listaUsuariosConectados;
	private JInternalFrame internalFrame;
	private JLabel lblIconeUsuario;

	public UIJanelaPrincipal(String ip) {

		this.getContentPane().setBackground(Color.WHITE);
		this.setBounds(100, 100, 579, 446);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);

		lblIconeUsuario = new JLabel("");
		lblIconeUsuario.setBounds(46, 11, 63, 64);
		lblIconeUsuario.setIcon(new ImageIcon(this.getClass().getResource("imagens/usuario_icon.png")));
		lblIconeUsuario.addMouseListener(this);
		this.getContentPane().add(lblIconeUsuario);

		JLabel nomeUsuario = new JLabel("    " + ip);
		nomeUsuario.setBounds(24, 78, 120, 20);
		this.getContentPane().add(nomeUsuario);

		setInternalFrame(new JInternalFrame("ANUNCIOS"));
		getInternalFrame().setBounds(154, 42, 409, 354);
		this.getContentPane().add(getInternalFrame());
		this.setVisible(true);
		getInternalFrame().setResizable(false);
		internalFrame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));

		JScrollPane scrollPane_1 = new JScrollPane();
		internalFrame.getContentPane().add(scrollPane_1);

		setPanel(new JPanel());
		scrollPane_1.setViewportView(getPanel());
		getPanel().setLayout(new GridLayout(6, 1, 0, 0));

		getInternalFrame().setVisible(true);

		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(10, 101, 141, 20);
		comboBox.addItem("Online");
		comboBox.addItem("Offline");
		comboBox.addItem("Ausente");
		comboBox.addItem("Ocupado");

		this.getContentPane().add(comboBox);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 134, 141, 262);
		this.getContentPane().add(scrollPane);

		panel_1 = new JPanel();
		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(new GridLayout(10, 1, 0, 0));

		JButton cadastrarAnuncio = new JButton("Cadastrar An\u00FAncio");
		cadastrarAnuncio.setForeground(Color.DARK_GRAY);
		cadastrarAnuncio.setBackground(Color.GREEN);
		cadastrarAnuncio.setBounds(154, 8, 152, 23);
		cadastrarAnuncio.addActionListener(new ActionCadastrar());
		this.getContentPane().add(cadastrarAnuncio);
		listaUsuariosConectados = new ArrayList<JButton>();
	}

	int k = 6;
	int j = 10;

	public void adicionarUsuariosOnline(final String ipConectado) {
		new Thread() {
			public void run() {
				j++;
				final JButton btnUsuarioConectado = new JButton(ipConectado);
				btnUsuarioConectado.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						setIpChat(ipConectado);
						ControleChatAplicacao.getInstance();
						UIJanelaChat.setIdJanela(ipConectado);
						if (btnUsuarioConectado.getBackground() == Color.ORANGE) {
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

		this.revalidate();
		this.repaint();
		panel_1.revalidate();
		panel_1.repaint();
	}

	public static UIJanelaPrincipal getInstance(String ip) {
		if (instancia == null) {
			return instancia = new UIJanelaPrincipal(ip);
		}
		return instancia;
	}
	
	public static UIJanelaPrincipal getInstance() {
		if (instancia == null) {
			return instancia = new UIJanelaPrincipal(new String());
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
		JLabel titulo = new JLabel(anuncio.getTitulo() + " (" + anuncio.getCategoria() + ")");
		JLabel imagem = new JLabel();

		imagem.setIcon(anuncio.getImagem());

		scrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPanel.setPreferredSize(new Dimension(5, 50));
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
		getPanel().add(painel);
		repintarTela();
	}

	public void mouseClicked(MouseEvent me){
		
		if(me.getSource() == lblIconeUsuario){
			trocarIconeUsuario();
		}
	}

	private void trocarIconeUsuario() {
		
		JFileChooser exploradorArquivos = new JFileChooser(System.getProperty("user.dir"));
		int valorRetornado = exploradorArquivos.showOpenDialog(this);
		
		File arquivoRetornado = null;
		
		if (valorRetornado == JFileChooser.APPROVE_OPTION) {
			 arquivoRetornado  = new File(exploradorArquivos.getSelectedFile(), "");
		}
		
		BufferedImage imagem = null;
		boolean isImagem = Utilitaria.identificarTipoArquivo(arquivoRetornado);

		if (isImagem) {

			try {
				imagem = ImageIO.read(arquivoRetornado);
			} catch (IOException io) {
				System.out.println(io.getMessage());
			}
		}else{
			try {
				imagem = ImageIO.read(this.getClass().getResource("imagens/arquivo_icon.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Image imagemDinamizada = imagem.getScaledInstance(lblIconeUsuario.getWidth(), lblIconeUsuario.getHeight(),
				Image.SCALE_SMOOTH);

		lblIconeUsuario.setIcon(new ImageIcon(imagemDinamizada));
		repintarTela();
	}
	
	public void notificarUsuario(String ipSocketEnviouMensagem) {
		if (UIJanelaChat.getInstance().getFocusableWindowState()) {
			for (JButton botaoLista : listaUsuariosConectados) {
				String ipUsuarioRemetente = botaoLista.getText();
				if (ipUsuarioRemetente.equals(ipSocketEnviouMensagem)) {
					botaoLista.setBackground(Color.ORANGE);
					repintarTela();
				}
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