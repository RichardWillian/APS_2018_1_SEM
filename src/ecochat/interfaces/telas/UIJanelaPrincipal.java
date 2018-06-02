package ecochat.interfaces.telas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

import com.sun.corba.se.impl.interceptors.InterceptorInvoker;
import com.sun.corba.se.impl.javax.rmi.CORBA.Util;

import ecochat.aplicacoes.servidor.ControleChatAplicacao;
import ecochat.aplicacoes.telas.JanelaBase;

@SuppressWarnings("serial")
public class UIJanelaPrincipal extends JanelaBase{

	private JFrame FrmEcOLX;
	private JPanel panel_1;
	private static UIJanelaPrincipal instancia;
	private String ipChat;

	public UIJanelaPrincipal(){
		FrmEcOLX = new JFrame();

		FrmEcOLX.getContentPane().setBackground(Color.WHITE);
		FrmEcOLX.setTitle("ECOLX");
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

		JInternalFrame internalFrame = new JInternalFrame("ANUNCIOS");
		internalFrame.setBounds(144, 11, 409, 385);
		FrmEcOLX.getContentPane().add(internalFrame);
		internalFrame.getContentPane().setLayout(null);
		FrmEcOLX.setVisible(true);

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Vitor\\Desktop\\produto.png"));
		lblNewLabel.setBounds(10, 11, 70, 57);
		internalFrame.getContentPane().add(lblNewLabel);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(83, 11, 300, 57);
		internalFrame.getContentPane().add(scrollPane_1);

		JTextArea descricao = new JTextArea();
		scrollPane_1.setViewportView(descricao);
		descricao.setWrapStyleWord(true);
		descricao.setFont(new Font("Arial", Font.PLAIN, 10));
		descricao.setEditable(false);
		descricao.setText(
				"Vendo pe\u00E7a eletronica com alguns wats e ela funciona em perfeito e nois que voa molecotinho da árabia estado troco por HD SSD. aquele abra\u00E7o amigos");
		descricao.setLineWrap(true);
		descricao.setAutoscrolls(true);

		JLabel label_1 = new JLabel("New label");
		label_1.setIcon(new ImageIcon("C:\\Users\\Vitor\\Desktop\\produto.png"));
		label_1.setBounds(10, 79, 70, 57);
		internalFrame.getContentPane().add(label_1);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(83, 79, 300, 57);
		internalFrame.getContentPane().add(scrollPane_2);

		JTextArea textArea = new JTextArea();
		scrollPane_2.setViewportView(textArea);
		textArea.setWrapStyleWord(true);
		textArea.setText(
				"Vendo pe\u00E7a eletronica com alguns wats e ela funciona em perfeito e nois que voa molecotinho da \u00E1rabia estado troco por HD SSD. aquele abra\u00E7o amigos");
		textArea.setLineWrap(true);
		textArea.setFont(new Font("Arial", Font.PLAIN, 10));
		textArea.setEditable(false);
		textArea.setAutoscrolls(true);

		JLabel label_2 = new JLabel("New label");
		label_2.setIcon(new ImageIcon("C:\\Users\\Vitor\\Desktop\\produto.png"));
		label_2.setBounds(10, 141, 70, 57);
		internalFrame.getContentPane().add(label_2);

		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(83, 141, 300, 57);
		internalFrame.getContentPane().add(scrollPane_3);

		JTextArea txtrVendoPeaEletronica = new JTextArea();
		scrollPane_3.setViewportView(txtrVendoPeaEletronica);
		txtrVendoPeaEletronica.setWrapStyleWord(true);
		txtrVendoPeaEletronica.setText(
				"Vendo pe\u00E7a eletronica com alguns wats e ela funciona em perfeito e nois que voa molecotinho da \u00E1rabia estado troco por HD SSD. aquele abra\u00E7o amigos eessa descri\u00E7\u00E3o E MAIOR PRA PODER MOSTRAR QUE O ANUNCIO TAMB\u00C9M TEM SCROLL.");
		txtrVendoPeaEletronica.setLineWrap(true);
		txtrVendoPeaEletronica.setFont(new Font("Arial", Font.PLAIN, 10));
		txtrVendoPeaEletronica.setEditable(false);
		txtrVendoPeaEletronica.setAutoscrolls(true);

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
	}

	// TODO VITOR - VERIFICAR SE VAI PASSAR EMAIL OU NOME PARA MUDAR O NOME DA
	// VARIÁVEL
	int j = 10;

	public void adicionarUsuariosOnline(final String emailConectado) {

		new Thread() {
			public void run() {
				j++;
				JButton btnUsuarioConectado = new JButton(emailConectado);
				btnUsuarioConectado.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent ae) {
						setIpChat(emailConectado);
						ControleChatAplicacao.getInstance();
					}
				});
				
				panel_1.add(btnUsuarioConectado);
				panel_1.setLayout(new GridLayout(j, 1, 0, 0));
				panel_1.revalidate();
				panel_1.repaint();
			}
		}.start();
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
}