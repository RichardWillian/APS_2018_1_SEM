

import java.io.BufferedReader;
import java.io.DataInputStream;
//import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClienteDois {

	private static Socket socket;

	public static void main(String[] args) {

		try {
			socket = new Socket("127.0.0.2", 12345);

			ObjectOutputStream fluxoSaidaDados = new ObjectOutputStream(socket.getOutputStream());

			BufferedReader leitorBuffered = new BufferedReader(new InputStreamReader(System.in));

			escreverMensagemAoServidor(fluxoSaidaDados, leitorBuffered);
			lerMensagemServidor();

		} catch (IOException iec) {
			System.out.println(iec.getMessage());
		}
	}

	private static void lerMensagemServidor() {
		new Thread(){
			public void run(){
				try {
					DataInputStream fluxoEntradaDados = new DataInputStream(socket.getInputStream());
					while(true){
					
						String mensagemOutroClienteQueVeioPeloServidor = fluxoEntradaDados.readUTF();
						System.out.println(mensagemOutroClienteQueVeioPeloServidor);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}

	private static void escreverMensagemAoServidor(final ObjectOutputStream fluxoSaidaDados, final BufferedReader leitorBuffered)
			throws IOException {

		new Thread() {
			public void run() {
				String mensagemSaida;
				try {
					while (true) {
//						mensagemSaida = leitorBuffered.readLine();
//						fluxoSaidaDados.writeUTF("Mensagem do Cliente (2): " + mensagemSaida + "=127.0.0.3");
						
						mensagemSaida = leitorBuffered.readLine();
						DadoCompartilhado dadoCompartilhado = new DadoCompartilhado();
						//fluxoSaidaDados.writeUTF("Mensagem do Cliente (1): " + mensagemSaida + "=127.0.0.2");
						dadoCompartilhado.setEmailEntrega("127.0.0.3");
						dadoCompartilhado.setMensagem("Mensagem do Cliente (2): " + mensagemSaida);
						
						fluxoSaidaDados.writeObject(dadoCompartilhado);
						
						fluxoSaidaDados.flush();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}
}