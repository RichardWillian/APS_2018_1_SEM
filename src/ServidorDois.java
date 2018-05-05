
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

public class ServidorDois {

	private static ServerSocket servidorSocket;
	private static Socket socket;

	public static void main(String[] args) {

		try {
			servidorSocket = new ServerSocket(12345);
			socket = servidorSocket.accept();

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

				try {
					receberArquivo("C:\\Users\\Seven\\Desktop\\Servidor\\VideoServidor.mp4");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			private void receberArquivo(String caminhoArquivo) throws IOException {
				
				final int TAMANHO_MEMORIA_TEMPORARIA_TRANSFERENCIA = 1024; // Memória temporária é o BUFFER
			    byte[] memoriaTemporaria;
			    
			    memoriaTemporaria = new byte[TAMANHO_MEMORIA_TEMPORARIA_TRANSFERENCIA];
			    
		          BufferedInputStream fluxoEntradaBuffer = new BufferedInputStream(socket.getInputStream());

		          BufferedOutputStream fluxoSaidaBuffer = 
		               new BufferedOutputStream(new FileOutputStream(caminhoArquivo));
		               
		          int tamanho = 0;
		          while ((tamanho = fluxoEntradaBuffer.read(memoriaTemporaria)) > 0) {
		        	  fluxoSaidaBuffer.write(memoriaTemporaria, 0, tamanho);
		          }
		          fluxoEntradaBuffer.close();
		          fluxoSaidaBuffer.flush();
		          fluxoSaidaBuffer.close();
		          socket.close();
		          System.out.println("\nRecebido com Sucesso!");
			}
		}.start();
	}
}