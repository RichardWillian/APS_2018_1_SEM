
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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

	private static void escreverMensagemAoServidor(final ObjectOutputStream fluxoSaidaDados,
			final BufferedReader leitorBuffered)

			throws IOException {

		new Thread() {
			public void run() {
				String mensagemSaida;
				try {
					while (true) {
						mensagemSaida = leitorBuffered.readLine();
						DadoCompartilhado dadoCompartilhado = new DadoCompartilhado();
						dadoCompartilhado.setEmailEntrega("127.0.0.2");
						dadoCompartilhado.setMensagem("Mensagem do Cliente (1): " + mensagemSaida);

						if (mensagemSaida.equals("Enviar")){
							dadoCompartilhado
									.setArquivo(new File("C:\\Users\\richard.divino\\Desktop\\Cliente\\extrato.mp4"));
							System.out.println("Arquivo enviado com sucesso!");
						}

						fluxoSaidaDados.writeObject(dadoCompartilhado);

						fluxoSaidaDados.flush();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();

		// new Thread() {
		// public void run() {
		//
		// try {
		// enviarArquivo("C:\\Users\\Seven\\Desktop\\Cliente\\VideoCliente.mp4");
		// } catch (Exception e) {
		//
		// e.printStackTrace();
		// }
		// }
		//
		// private void enviarArquivo(String caminhoArquivo)
		// throws IOException, ClassNotFoundException, InterruptedException {
		//
		// final int TAMANHO_MEMORIA_TEMPORARIA_TRANSFERENCIA = 1024 * 50;
		// byte[] memoriaTemporaria;
		//
		// memoriaTemporaria = new
		// byte[TAMANHO_MEMORIA_TEMPORARIA_TRANSFERENCIA];
		//
		// BufferedInputStream fluxoEntradaBuffer = new BufferedInputStream(new
		// FileInputStream(caminhoArquivo));
		//
		// BufferedOutputStream fluxoSaidaBuffer = new
		// BufferedOutputStream(socket.getOutputStream());
		//
		// int tamanho = 0;
		// while ((tamanho = fluxoEntradaBuffer.read(memoriaTemporaria)) > 0) {
		// fluxoSaidaBuffer.write(memoriaTemporaria, 0, tamanho);
		// }
		// fluxoEntradaBuffer.close();
		// fluxoSaidaBuffer.flush();
		// fluxoSaidaBuffer.close();
		// socket.close();
		// socket.close();
		// System.out.println("\nEnviado com Sucesso!");
		// }
		// }.start();
	}

	private static void lerMensagemServidor() {
		new Thread() {

			public void run() {
				try {
					while (true) {
						ObjectInputStream fluxoEntradaDados = new ObjectInputStream(socket.getInputStream());
						DadoCompartilhado dadoCompatilhado = (DadoCompartilhado) fluxoEntradaDados.readObject();
						System.out.println(dadoCompatilhado.getMensagem());

						if (dadoCompatilhado.getArquivo() != null) {
							InputStream entradaArquivo = null;
							OutputStream saidaArquivo = null;

							try {
								entradaArquivo = new FileInputStream(dadoCompatilhado.getArquivo());
								saidaArquivo = new FileOutputStream(
										new File("C:\\Users\\richard.divino\\Desktop\\Cliente\\extratoTres.mp4"));

								byte[] memoriaTemporaria = new byte[1024 * 50];
								int tamanho;
								while ((tamanho = entradaArquivo.read(memoriaTemporaria)) > 0) {
									saidaArquivo.write(memoriaTemporaria, 0, tamanho);
								}
								System.out.println("Recebido com sucesso!");
							} catch (Exception ex) {
								System.err.println(ex.getMessage());
							}
						}
					}
				} catch (IOException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception ex) {
					System.out.println("Maior que meus erros");
				}
			}
		}.start();
	}
}