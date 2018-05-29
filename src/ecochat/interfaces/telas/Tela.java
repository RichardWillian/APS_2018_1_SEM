package ecochat.interfaces.telas;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Tela extends JFrame {

	public static void main(String[] args) {

		JFrame.setDefaultLookAndFeelDecorated(true);
		UIJanelaLogin l = new UIJanelaLogin();
		l.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		l.setTitle("EcoLx Login");
		l.setBounds(300, 300, 350, 200);
		l.setVisible(true);
		l.setLocationRelativeTo(null);

	}

}
