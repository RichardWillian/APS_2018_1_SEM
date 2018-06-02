
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import ecochat.entidades.DadoCompartilhado;
import ecochat.utilitarios.ConstantesGerais;

public class ClienteTres {

	private static Socket socketServidorCentral;
	private static Socket socketServidorChat;
	private static ObjectOutputStream fluxoSaidaDados;
	private static BufferedReader leitorBuffered;

	public static void main(String[] args) throws InterruptedException {

		try {

			String ipMaquina = "127.0.0.4";
			
			socketServidorCentral = new Socket(InetAddress.getByName(ConstantesGerais.IP_SERVIDOR_CENTRAL),
					ConstantesGerais.PORTA_SERVIDOR_CENTRAL, InetAddress.getByName(ipMaquina), 0);
			Thread.sleep(1000);
			socketServidorChat = new Socket(InetAddress.getByName(ConstantesGerais.IP_SERVIDOR_CHAT),
					ConstantesGerais.PORTA_SERVIDOR_CHAT, InetAddress.getByName(ipMaquina), 0);

			fluxoSaidaDados = new ObjectOutputStream(socketServidorChat.getOutputStream());

			leitorBuffered = new BufferedReader(new InputStreamReader(System.in));

			escreverMensagemAoServidor(fluxoSaidaDados, leitorBuffered);
			lerMensagemServidor();

		} catch (IOException iec) {
			System.out.println(iec.getMessage());
		}
	}

	private static void lerMensagemServidor() {
		new Thread() {

			@SuppressWarnings("static-access")
			public void run() {
				try {
					while (true) {
						ObjectInputStream fluxoEntradaDados = new ObjectInputStream(socketServidorChat.getInputStream());
						DadoCompartilhado dadoCompatilhado = (DadoCompartilhado) fluxoEntradaDados.readObject();
						System.out.println(dadoCompatilhado.getMensagem());

						if (dadoCompatilhado.getArquivo() != null) {
							InputStream entradaArquivo = null;
							OutputStream saidaArquivo = null;

							try {
								dadoCompatilhado.getArquivo().createTempFile("C:\\Desktop\\", "Cliente");
								entradaArquivo = new FileInputStream(dadoCompatilhado.getArquivo());
								saidaArquivo = new FileOutputStream(
										new File("C:\\Users\\Seven\\Desktop\\Cliente\\PQPDeuCerto.jpg"));

								byte[] memoriaTemporaria = new byte[1024 * 50];
								int tamanho;
								while ((tamanho = entradaArquivo.read(memoriaTemporaria)) > 0) {
									saidaArquivo.write(memoriaTemporaria, 0, tamanho);
								}
								System.out.println("Recebido com sucesso!");
							} catch (Exception ex) {
								System.err.println(ex.getMessage());
							} finally {
								entradaArquivo.close();
								saidaArquivo.flush();
								saidaArquivo.close();
							}
						}
					}
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				} catch (Exception ex) {
					System.out.println("Maior que meus erros");
				}
			}
		}.start();
	}

	private static void escreverMensagemAoServidor(final ObjectOutputStream fluxoSaidaDados,
			final BufferedReader leitorBuffered) throws IOException {

		new Thread() {
			public void run() {
				String mensagemSaida;
				try {
					while (true) {
						mensagemSaida = leitorBuffered.readLine();
						DadoCompartilhado dadoCompartilhado = new DadoCompartilhado();
						dadoCompartilhado.setEmailEntrega("127.0.0.2");
						dadoCompartilhado.setMensagem(mensagemSaida);
						if (mensagemSaida.equals("Enviar")) {
							dadoCompartilhado
									.setArquivo(new File("C:\\Users\\Seven\\Desktop\\Cliente\\ImagemCliente.jpg"));
							System.out.println("Arquivo enviado com sucesso!");
						}

						fluxoSaidaDados.writeObject(dadoCompartilhado);
						fluxoSaidaDados.flush();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}