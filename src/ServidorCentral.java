import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorCentral {

	private static ServerSocket servidorCentralSocket;
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		 try {
			servidorCentralSocket = new ServerSocket(12345);
			 Socket socket = servidorCentralSocket .accept();
			 DataInputStream fluxoEntradaDados = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
