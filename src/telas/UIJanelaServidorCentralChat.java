package telas;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import chat.ServidorCentral;
import interfaces.IJanelaBase;

@SuppressWarnings("serial")
public class UIJanelaServidorCentralChat extends JanelaBase implements IJanelaBase {

	private static UIJanelaServidorCentralChat instancia;
	private JButton botaoIniciarServidor;
	private JButton botaoDesligarServidor;
	private JTextArea txtAreaUsuariosConectados;

	public UIJanelaServidorCentralChat() {

		instanciarComponentes();
		setProriedadesJanela();
		adicionarComponentesTela();
		setPropriedadesComponentes();
	}

	public static UIJanelaServidorCentralChat getInstance() {

		if (instancia == null)
			return instancia = new UIJanelaServidorCentralChat();

		return instancia;
	}

	@Override
	public void instanciarComponentes() {
		botaoIniciarServidor = new JButton();
		botaoDesligarServidor = new JButton();
		txtAreaUsuariosConectados = new JTextArea();
	}

	@Override
	public void setProriedadesJanela() {
		this.setLocation(150, 25);
		this.setSize(300, 450);
		this.setVisible(true);
		this.setResizable(false);
		this.setTitle("Servidor Central Chat");
	}

	@Override
	public void adicionarComponentesTela() {
		this.add(botaoIniciarServidor);
		this.add(botaoDesligarServidor);
		this.add(txtAreaUsuariosConectados);
	}

	@Override
	public void setPropriedadesComponentes() {

		botaoIniciarServidor.setVisible(true);
		botaoIniciarServidor.setBounds(20, 320, 100, 40);
		botaoIniciarServidor.addActionListener(this);
		botaoIniciarServidor.setText("Iniciar");

		botaoDesligarServidor.setVisible(true);
		botaoDesligarServidor.setBounds(170, 320, 100, 40);
		botaoDesligarServidor.addActionListener(this);
		botaoDesligarServidor.setText("Desligar");
		
		txtAreaUsuariosConectados.setVisible(true);
		txtAreaUsuariosConectados.setBounds(30, 30, 230, 260);
		txtAreaUsuariosConectados.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == botaoIniciarServidor) {

			new Thread() {
				public void run() {
					ServidorCentral.getInstance().iniciarServidor();
				}
			}.start();
		}

		if (ae.getSource() == botaoDesligarServidor) {

			int desligar = JOptionPane.showConfirmDialog(null,
					"Se desligar o servidor todos os usuários serão desconctados instantaneamente.\n\n"
							+ "Deseja realmente fazer isso?");

			if (desligar == 0) {
				new Thread() {
					public void run() {
						ServidorCentral.getInstance().desligarServidor();
					}
				}.start();
			}
		}
	}
	
	public void mostrarMensagem(String mensagem){
		txtAreaUsuariosConectados.setText(txtAreaUsuariosConectados.getText() + mensagem + "\n");
	}
	

	
	@Override
	public void windowClosing(WindowEvent we) {
		ServidorCentral.getInstance().desligarServidor();
	}
}
