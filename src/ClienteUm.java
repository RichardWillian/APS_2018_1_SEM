

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class ClienteUm {

	private static Socket socket;

	public static void main(String[] args) {

		try {
			socket = new Socket("127.0.0.3", 12345);

			ObjectOutputStream fluxoSaidaDados = new ObjectOutputStream(socket.getOutputStream());

			BufferedReader leitorBuffered = new BufferedReader(new InputStreamReader(System.in));

			escreverMensagemAoServidor(fluxoSaidaDados, leitorBuffered);
			lerMensagemServidor();

		} catch (IOException iec) {
			System.out.println(iec.getMessage());
		}
	}

	private static void escreverMensagemAoServidor(final ObjectOutputStream fluxoSaidaDados, final BufferedReader leitorBuffered)

			throws IOException {

		new Thread() {
			public void run() {
				String mensagemSaida;
				try {
					while (true) {
						mensagemSaida = leitorBuffered.readLine();
						DadoCompartilhado dadoCompartilhado = new DadoCompartilhado();
						//fluxoSaidaDados.writeUTF("Mensagem do Cliente (1): " + mensagemSaida + "=127.0.0.2");
						dadoCompartilhado.setEmailEntrega("127.0.0.2");
						dadoCompartilhado.setMensagem("Mensagem do Cliente (1): " + mensagemSaida);
						
						fluxoSaidaDados.writeObject(dadoCompartilhado);
						
						fluxoSaidaDados.flush();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
		
//		new Thread() {
//			public void run() {
//
//				try {
//					enviarArquivo("C:\\Users\\Seven\\Desktop\\Cliente\\VideoCliente.mp4");
//				} catch (Exception e) {
//
//					e.printStackTrace();
//				}
//			}
//
//			private void enviarArquivo(String caminhoArquivo)
//					throws IOException, ClassNotFoundException, InterruptedException {
//
//				final int TAMANHO_MEMORIA_TEMPORARIA_TRANSFERENCIA = 1024 * 50; // Memória
//																				// temporária
//																				// é
//																				// o
//																				// BUFFER
//				byte[] memoriaTemporaria;
//
//				memoriaTemporaria = new byte[TAMANHO_MEMORIA_TEMPORARIA_TRANSFERENCIA];
//
//				BufferedInputStream fluxoEntradaBuffer = new BufferedInputStream(new FileInputStream(caminhoArquivo));
//
//				BufferedOutputStream fluxoSaidaBuffer = new BufferedOutputStream(socket.getOutputStream());
//
//				int tamanho = 0;
//				while ((tamanho = fluxoEntradaBuffer.read(memoriaTemporaria)) > 0) {
//					fluxoSaidaBuffer.write(memoriaTemporaria, 0, tamanho);
//				}
//				fluxoEntradaBuffer.close();
//				fluxoSaidaBuffer.flush();
//				fluxoSaidaBuffer.close();
//				socket.close();
//				socket.close();
//				System.out.println("\nEnviado com Sucesso!");
//			}
//		}.start();
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
}