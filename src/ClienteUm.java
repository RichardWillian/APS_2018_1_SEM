
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
//import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import com.sun.jndi.cosnaming.IiopUrl.Address;

public class ClienteUm {

	private static Socket socket;

	public static void main(String[] args) {

		try {
			socket = new Socket("127.0.0.1", 12345);

			DataOutputStream fluxoSaidaDados = new DataOutputStream(socket.getOutputStream());

			BufferedReader leitorBuffered = new BufferedReader(new InputStreamReader(System.in));

			escreverMensagemAoServidor(fluxoSaidaDados, leitorBuffered);

		} catch (IOException iec) {
			System.out.println(iec.getMessage());
		}
	}

	private static void escreverMensagemAoServidor(DataOutputStream fluxoSaidaDados, BufferedReader leitorBuffered)
			throws IOException {

		new Thread() {
			public void run() {

					try {
					enviarArquivo("C:\\Users\\Seven\\Desktop\\Cliente\\VideoCliente.mp4");
					} catch (Exception e) {
						
						e.printStackTrace();
					}
			}

			private void enviarArquivo(String caminhoArquivo) throws IOException, ClassNotFoundException, InterruptedException{
				
				final int TAMANHO_MEMORIA_TEMPORARIA_TRANSFERENCIA = 1024 * 50; // Memória temporária é o BUFFER
			    byte[] memoriaTemporaria;
			    
			    memoriaTemporaria = new byte[TAMANHO_MEMORIA_TEMPORARIA_TRANSFERENCIA];

			     
			          BufferedInputStream fluxoEntradaBuffer = new BufferedInputStream(new FileInputStream(caminhoArquivo));

			          BufferedOutputStream fluxoSaidaBuffer = new BufferedOutputStream(socket.getOutputStream());
			               
			          
			          int tamanho = 0;
			          while ((tamanho = fluxoEntradaBuffer.read(memoriaTemporaria)) > 0) {
			        	  fluxoSaidaBuffer.write(memoriaTemporaria, 0, tamanho);
			          }
			          fluxoEntradaBuffer.close();
			          fluxoSaidaBuffer.flush();
			          fluxoSaidaBuffer.close();
			          socket.close();
			          socket.close();
			          System.out.println("\nEnviado com Sucesso!");
			}
		}.start();

	}
}