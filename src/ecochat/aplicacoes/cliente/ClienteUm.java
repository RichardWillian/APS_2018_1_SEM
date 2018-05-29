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
import ecochat.interfaces.telas.JanelaChat;

public class ClienteUm {

	private static Socket socket;
	private static ObjectOutputStream fluxoSaidaDados;
	@SuppressWarnings("unused")
	private static BufferedReader leitorBuffered;
	private static ClienteUm instancia;

	public static void main(String[] args) {

		// if (verificarAutenticacaoUsuario()) {
		try {
			socket = new Socket(InetAddress.getByName("127.0.0.1"), 12345, InetAddress.getByName("127.0.0.2"), 0);
			fluxoSaidaDados = new ObjectOutputStream(socket.getOutputStream());
			JanelaChat.getInstance();
		} catch (IOException e) {
			e.printStackTrace();
		}

		leitorBuffered = new BufferedReader(new InputStreamReader(System.in));

		entrarChat();
		// }
	}

	@SuppressWarnings({ "resource", "unused" })
	private static boolean verificarAutenticacaoUsuario() {

		try {
			Socket socketAutenticacao = new Socket(InetAddress.getByName("127.255.255.254"), 12346,
					InetAddress.getByName("127.0.0.3"), 0);

			ObjectOutputStream fluxoSaidaDadosAutenticacao = new ObjectOutputStream(
					socketAutenticacao.getOutputStream());

			DadoAutenticacao dadoAutenticacao = new DadoAutenticacao();

			dadoAutenticacao.setEmail("Jorginho@hotmail.com");
			dadoAutenticacao.setSenha("kli");

			fluxoSaidaDadosAutenticacao.writeObject(dadoAutenticacao);
			fluxoSaidaDadosAutenticacao.flush();

			ObjectInputStream fluxoEntradaDados = new ObjectInputStream(socketAutenticacao.getInputStream());
			boolean isUsuarioAutenticado = false;
			try {
				isUsuarioAutenticado = (boolean) fluxoEntradaDados.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			return isUsuarioAutenticado;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void escreverMensagemAoServidor(final DadoCompartilhado dadoCompartilhado) {

		new Thread() {
			public void run() {
				try {
					dadoCompartilhado.setEmailEntrega("127.0.0.3");

					if (dadoCompartilhado.getArquivo() != null) {
						Thread.sleep(2000);
						fluxoSaidaDados.writeObject(dadoCompartilhado);
						JanelaChat.getInstance().trocarLoadingPorImagemArquivo();
					} else {
						fluxoSaidaDados.writeObject(dadoCompartilhado);
					}

					fluxoSaidaDados.flush();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
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

						JanelaChat.getInstance().adicionarMensagemEsquerda(dadoCompatilhado.getMensagem());

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
					e.printStackTrace();
				} catch (Exception ex) {
					System.out.println("Maior que meus erros");
				}
			}
		}.start();
	}

	private static void entrarChat() {
		lerMensagemServidor();
	}

	public static ClienteUm getInstance() {

		if (instancia == null)
			return instancia = new ClienteUm();

		return instancia;
	}
}