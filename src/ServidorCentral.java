import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServidorCentral {

	private static ServerSocket socketServidorCentral;
	private static List<Socket> socketsConectados;

	public static void main(String[] args) {

		try {
			socketServidorCentral = new ServerSocket(12345);
			socketsConectados = new ArrayList<Socket>();
			do {
				Socket socket = socketServidorCentral.accept();
				ObjectInputStream fluxoEntradaDados = new ObjectInputStream(socket.getInputStream());
				System.out.println("Cliente "+ socket.getLocalAddress().getHostAddress() +" se conectou! ");
				socketsConectados.add(socket);
				lerMensagemDoCliente(fluxoEntradaDados);
				
			} while (true);

		} catch (Exception e) {
			System.err.println("Ops! " + e.getMessage());
		}
	}

	private static void lerMensagemDoCliente(final ObjectInputStream fluxoEntradaDados) {
		new Thread() {
			public void run() {
				String mensagemEntrada = "";

				try {
					while (true) {
						//mensagemEntrada = fluxoEntradaDados.readUTF();
						//String[] teste = mensagemEntrada.split("=");
//						String mensagem = teste[0];
//						String paraQuemEnviar = teste[1];
//						
						Socket socketQueReceberaMensagem = null; //socketsConectados.stream()
						//.filter(x -> x.getLocalAddress().getHostAddress().equals(paraQuemEnviar))
						//.findFirst().get();
						
						DadoCompartilhado dadoCompartilhado = (DadoCompartilhado) fluxoEntradaDados.readObject();

						for (Socket socketConectado : socketsConectados) {
							if(socketConectado.getLocalAddress().getHostAddress().equals(dadoCompartilhado.getEmailEntrega())){
								socketQueReceberaMensagem = socketConectado;
							}
						}
						
						DataOutputStream fluxoSaidaDados = new DataOutputStream(socketQueReceberaMensagem.getOutputStream());
						fluxoSaidaDados.writeUTF(dadoCompartilhado.getMensagem());
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}

}
