package ecochat.interfaces.telas;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import ecochat.aplicacoes.servidor.ServidorCentral;
import ecochat.aplicacoes.telas.JanelaBase;

@SuppressWarnings("serial")
public class TelaEnvioArquivo extends JanelaBase{
	
	private JFrame frame;
	private JTextField textDescricao;
	private JTextField textTitulo;
	private JTextField textCategoria;
	private JButton label_1;
	private JButton label_2;
	private JTextField textLocalizacao;
	private final JLabel lblNewLabel_1 = new JLabel("New label");

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaEnvioArquivo window = new TelaEnvioArquivo();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaEnvioArquivo() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setForeground(new Color(240, 255, 240));
		frame.setBounds(100, 100, 474, 430);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblDescrio = new JLabel("Descri\u00E7\u00E3o");
		lblDescrio.setBounds(207, 205, 223, 14);
		frame.getContentPane().add(lblDescrio);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(413, 220, 17, 20);
		frame.getContentPane().add(scrollBar);
		
		textDescricao = new JTextField();
		textDescricao.setBounds(207, 220, 207, 20);
		frame.getContentPane().add(textDescricao);
		textDescricao.setColumns(10);
		
		JLabel lblTtulo = new JLabel("T\u00EDtulo");
		lblTtulo.setBounds(207, 160, 223, 14);
		frame.getContentPane().add(lblTtulo);
		
		textTitulo = new JTextField();
		textTitulo.setColumns(10);
		textTitulo.setBounds(207, 175, 223, 20);
		frame.getContentPane().add(textTitulo);
		
		JButton btnPublicar = new JButton("PUBLICAR");
		btnPublicar.setBounds(207, 345, 223, 30);
		frame.getContentPane().add(btnPublicar);
		
		JLabel lblLocalizao = new JLabel("Localiza\u00E7\u00E3o");
		lblLocalizao.setBounds(207, 295, 223, 14);
		frame.getContentPane().add(lblLocalizao);
		
		JLabel lblCategoria = new JLabel("Categoria");
		lblCategoria.setBounds(207, 250, 223, 14);
		frame.getContentPane().add(lblCategoria);
		
		
		
		textCategoria = new JTextField();
		textCategoria.setColumns(10);
		textCategoria.setBounds(207, 265, 223, 20);
		frame.getContentPane().add(textCategoria);
		
		textLocalizacao = new JTextField();
		textLocalizacao.setColumns(10);
		textLocalizacao.setBounds(207, 310, 223, 20);
		frame.getContentPane().add(textLocalizacao);
		
		label_1 = new JButton("");
		label_1.setIcon(new ImageIcon(TelaEnvioArquivo.class.getResource("/images/Inserir.png")));
		label_1.setBounds(331, 49, 100, 100);
		frame.getContentPane().add(label_1);
		
		label_2 = new JButton("");
		label_2.setIcon(new ImageIcon(TelaEnvioArquivo.class.getResource("/images/Inserir.png")));
		label_2.setBounds(193, 49, 100, 100);
		frame.getContentPane().add(label_2);	
		
		label_1.addActionListener(this);
		label_2.addActionListener(this);
			
		
		JLabel lblInserirAnncio = new JLabel("                                           Inserir An\u00FAncio");
		lblInserirAnncio.setForeground(Color.WHITE);
		lblInserirAnncio.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblInserirAnncio.setBounds(0, 0, 458, 30);
		frame.getContentPane().add(lblInserirAnncio);
		lblNewLabel_1.setIcon(new ImageIcon(TelaEnvioArquivo.class.getResource("/images/Barra.png")));
		lblNewLabel_1.setBounds(0, 0, 458, 31);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(TelaEnvioArquivo.class.getResource("/images/background3.jpg")));
		label.setBounds(0, 0, 458, 391);
		frame.getContentPane().add(label);
		frame.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{frame.getContentPane()}));
		
	}
	
	public void procuraArquivo (){
		String diretorioBase = ("C:\\Users\\richard.divino\\Desktop\\Cliente");
		File dir = new File(diretorioBase);
		
		JFileChooser choose = new JFileChooser();
		choose.setCurrentDirectory(dir);
		
		int valorRetornado = choose.showOpenDialog(this);
		
		if (valorRetornado == JFileChooser.APPROVE_OPTION) {
			File arquivoEnvio = new File(choose.getSelectedFile(), "");
		} else {
			System.out.println("Nenhum arquivo selecionado");
		}
		
	}
	
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == label_1 || ae.getSource() == label_2) {
			
			new Thread() {
				public void run() {
					procuraArquivo ();
				}
			}.start();
		}}
	
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
	private class SwingAction_1 extends AbstractAction {
		public SwingAction_1() {
			putValue(NAME, "SwingAction_1");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
