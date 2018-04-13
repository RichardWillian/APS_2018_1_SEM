
//import java.io.BufferedReader;
import java.io.DataInputStream;
//import java.io.DataOutputStream;
import java.io.IOException;
//import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorDois {

	private static ServerSocket servidorSocket;

	public static void main(String[] args) {

		try {
			servidorSocket = new ServerSocket(12345);
			Socket socket = servidorSocket.accept();

			DataInputStream fluxoEntradaDados = new DataInputStream(socket.getInputStream());
			System.out.println("Cliente (1) conectado: " + servidorSocket.getInetAddress());
			lerMensagemDoCliente(fluxoEntradaDados);

		} catch (Exception e) {
			System.err.println("Ops! " + e.getMessage());
		}
	}

	private static void lerMensagemDoCliente(DataInputStream fluxoEntradaDados) {
		new Thread() {
			public void run() {
				String mensagemEntrada = "";

				try {
					while (true) {
						mensagemEntrada = fluxoEntradaDados.readUTF();
						System.out.println("\n" + mensagemEntrada);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}