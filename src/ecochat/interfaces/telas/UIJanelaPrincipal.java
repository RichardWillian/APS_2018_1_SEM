package ecochat.interfaces.telas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

import ecochat.aplicacoes.servidor.ControleChatAplicacao;
import ecochat.aplicacoes.telas.JanelaBase;
import java.awt.GridBagLayout;
import java.awt.TextArea;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import java.awt.ScrollPane;
import java.awt.Panel;
import javax.swing.JList;

@SuppressWarnings("serial")
public class UIJanelaPrincipal extends JanelaBase {

	private JFrame FrmEcOLX;
	private JPanel panel_1;
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

		JLabel label = new JLabel("");
		label.setBounds(46, 11, 55, 64);
		label.setIcon(new ImageIcon("C:\\Users\\Vitor\\Desktop\\testeaps.png"));
		FrmEcOLX.getContentPane().add(label);

		JTextPane txtpnVitorSantos = new JTextPane();
		txtpnVitorSantos.setBounds(42, 76, 69, 14);
		txtpnVitorSantos.setEnabled(false);
		txtpnVitorSantos.setEditable(false);
		txtpnVitorSantos.setText("Vitor Santos");
		FrmEcOLX.getContentPane().add(txtpnVitorSantos);
		
		setInternalFrame(new JInternalFrame("ANUNCIOS"));
		getInternalFrame().setBounds(154, 42, 409, 354);
		FrmEcOLX.getContentPane().add(getInternalFrame());
		FrmEcOLX.setVisible(true);
		getInternalFrame().setResizable(false);
		internalFrame.getContentPane().setLayout(new GridLayout(6, 0, 0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		internalFrame.getContentPane().add(scrollPane_1);
		
		JButton btnNewButton = new JButton("New button");
		internalFrame.getContentPane().add(btnNewButton);
//		btnNewButton.addActionListener(new ActionAddBotao());
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
		//ControleChatAplicacao.lerMensagemServidor();
		listaUsuariosConectados = new ArrayList<JButton>();
	}

	int k = 6;
	// TODO VITOR - VERIFICAR SE VAI PASSAR EMAIL OU NOME PARA MUDAR O NOME DA
	// VARIÁVEL
	int j = 10;
//	private Timer timer;
//	private Random rand = new Random();
//	private int delay = 300; // a cada 0,3 segundos
	
	public void adicionarUsuariosOnline(final String emailConectado) {
		new Thread() {
			public void run() {
				j++;
				JButton btnUsuarioConectado = new JButton(emailConectado);
				btnUsuarioConectado.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						setIpChat(emailConectado);
						ControleChatAplicacao.getInstance();
						UIJanelaChat.setIdJanela(emailConectado);
					}
				});

				listaUsuariosConectados.add(btnUsuarioConectado);
				panel_1.add(btnUsuarioConectado);
				panel_1.setLayout(new GridLayout(j, 1, 0, 0));
				repintarTela();
			}
		}.start();
	}

	public void alertaMensagem(String emailConectado) {
		
		if (UIJanelaChat.getIdJanela() == emailConectado) {
			if (UIJanelaChat.isMensagemNaFila()) {
				
				for(JButton botaoLista : listaUsuariosConectados) {
					if(botaoLista.getText() == emailConectado) {
						botaoLista.setBackground(Color.CYAN);
						repintarTela();
					}
				}
			}
		}
	}
	
	private void repintarTela(){
		
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

	private class ActionCadastrar implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			new UIJanelaEnvioArquivo();
		}
		
	}
//	int i =1;
//	private class ActionAddBotao implements ActionListener{
//
//		@Override
//		public void actionPerformed(ActionEvent arg0) {
//			getInternalFrame().getContentPane().add(new JButton("TESTE"+i++));
//			internalFrame.getContentPane().setLayout(new GridLayout(k, 0, 0, 0));
//			JButton btnNewButton = new JButton("New button");
//			btnNewButton.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//				}
//			});
//			scrollPane_1.setViewportView(btnNewButton);
//			getInternalFrame().revalidate();
//			getInternalFrame().repaint();
//			k++;
//		}
	//}
}