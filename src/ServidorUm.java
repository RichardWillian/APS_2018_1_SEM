
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServidorUm {

	private static ServerSocket servidorSocket;
	private static List<Socket> socketsConectados;

	public static void main(String[] args) {

		try {
			servidorSocket = new ServerSocket(12345);
			socketsConectados = new ArrayList<Socket>();
			do {
				Socket socket = servidorSocket.accept();
				DataInputStream fluxoEntradaDados = new DataInputStream(socket.getInputStream());
				System.out.println("Cliente "+ socket.getLocalAddress().getHostAddress() +" se conectou! ");
				socketsConectados.add(socket);
				lerMensagemDoCliente(fluxoEntradaDados);
				
			} while (true);

		} catch (Exception e) {
			System.err.println("Ops! " + e.getMessage());
		}
	}

	private static void lerMensagemDoCliente(final DataInputStream fluxoEntradaDados) {
		new Thread() {
			public void run() {
				String mensagemEntrada = "";

				try {
					while (true) {
						mensagemEntrada = fluxoEntradaDados.readUTF();
						String[] teste = mensagemEntrada.split("=");
						String mensagem = teste[0];
						String paraQuemEnviar = teste[1];
						
						Socket socketQueReceberaMensagem = null; //socketsConectados.stream()
						//.filter(x -> x.getLocalAddress().getHostAddress().equals(paraQuemEnviar))
						//.findFirst().get();

						for (Socket socketConectado : socketsConectados) {
							if(socketConectado.getLocalAddress().getHostAddress().equals(paraQuemEnviar)){
								socketQueReceberaMensagem = socketConectado;
							}
						}
						
						DataOutputStream fluxoSaidaDados = new DataOutputStream(socketQueReceberaMensagem.getOutputStream());
						fluxoSaidaDados.writeUTF(mensagem);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}