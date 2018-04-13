

import java.io.BufferedReader;
//import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClienteUm {

	private static Socket socket;

	public static void main(String[] args) {

		try {
			socket = new Socket("127.0.0.1", 12345);
			
			//DataInputStream fluxoEntradaDados = new DataInputStream(socket.getInputStream());
			DataOutputStream fluxoSaidaDados = new DataOutputStream(socket.getOutputStream());

			BufferedReader leitorBuffered = new BufferedReader(new InputStreamReader(System.in));

			// while (true) {

			escreverMensagemAoServidor(fluxoSaidaDados, leitorBuffered);

			// new Thread() {

			// public void run() {

			//lerMensagemDoServidor(fluxoEntradaDados);
			// }

			// }.start();
			// }

		} catch (IOException iec) {
			System.out.println(iec.getMessage());
		}
	}

	private static void escreverMensagemAoServidor(DataOutputStream fluxoSaidaDados, BufferedReader leitorBuffered)
			throws IOException {

		new Thread() {
			public void run() {
				String mensagemSaida;
				try {
					while (true) {
						mensagemSaida = leitorBuffered.readLine();
						fluxoSaidaDados.writeUTF("Mensagem do Cliente (1): " + mensagemSaida);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();

	}

//	private static void lerMensagemDoServidor(DataInputStream fluxoEntradaDados) {
//
//		new Thread() {
//			public void run() {
//				String mensagemEntrada = "";
//				while (true) {
//					try {
//						mensagemEntrada = fluxoEntradaDados.readUTF();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					System.out.println(mensagemEntrada);
//				}
//			}
//		}.start();
//	}
}