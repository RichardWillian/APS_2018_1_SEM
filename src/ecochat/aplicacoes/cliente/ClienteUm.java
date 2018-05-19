package ecochat.aplicacoes.cliente;

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

import ecochat.entidades.DadoAutenticacao;
import ecochat.entidades.DadoCompartilhado;

public class ClienteUm {

	private static Socket socket;
	private static ObjectOutputStream fluxoSaidaDados;
	private static BufferedReader leitorBuffered;

	public static void main(String[] args) {

		try {
			socket = new Socket(InetAddress.getByName("127.0.0.1"), 12345, InetAddress.getByName("127.0.0.3"), 0);
			fluxoSaidaDados = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		leitorBuffered = new BufferedReader(new InputStreamReader(System.in));

		if (verificarAutenticacaoUsuario())
			entrarChat();
	}

	@SuppressWarnings("resource")
	private static boolean verificarAutenticacaoUsuario() {

		try {
			Socket socketAutenticacao = new Socket(InetAddress.getByName("127.255.255.254"), 12346,
					InetAddress.getByName("127.0.0.3"), 0);
			ObjectOutputStream fluxoSaidaDadosAutenticacao = new ObjectOutputStream(
					socketAutenticacao.getOutputStream());

			DadoAutenticacao dadoAutenticacao = new DadoAutenticacao();
			dadoAutenticacao.setEmail("127.0.0.2");

			fluxoSaidaDadosAutenticacao.writeObject(dadoAutenticacao);
			fluxoSaidaDadosAutenticacao.flush();

			ObjectInputStream fluxoEntradaDados = new ObjectInputStream(socketAutenticacao.getInputStream());
			boolean isUsuarioAutenticado = fluxoEntradaDados.readBoolean();

			return isUsuarioAutenticado;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private static void escreverMensagemAoServidor() throws IOException {

		new Thread() {
			public void run() {
				String mensagemSaida;
				try {
					while (true) {
						mensagemSaida = leitorBuffered.readLine();
						DadoCompartilhado dadoCompartilhado = new DadoCompartilhado();
						dadoCompartilhado.setEmailEntrega("127.0.0.2");
						dadoCompartilhado.setMensagem("Mensagem do Cliente (1): " + mensagemSaida);

						if (mensagemSaida.equals("Enviar")) {
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

	public static void entrarChat() {
		try {

			escreverMensagemAoServidor();
			lerMensagemServidor();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}