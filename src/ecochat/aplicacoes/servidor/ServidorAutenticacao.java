package ecochat.aplicacoes.servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import ecochat.entidades.DadoAutenticacao;
import ecochat.utilitarios.ConstantesGerais;

public class ServidorAutenticacao {

	private static ServerSocket socketServidorAutenticacao;
	private static ServidorAutenticacao instancia;

	public void iniciarServidor() {

		new Thread() {
			public void run() {

				try {
					socketServidorAutenticacao = new ServerSocket(
							ConstantesGerais.PORTA_SERVIDOR_AUTENTICACAO, 
							ConstantesGerais.QUANTIDADE_MAXIMA_CONECTADOS, 
							InetAddress.getByName(ConstantesGerais.IP_SERVIDOR_AUTENTICACAO));

					while (true) {

						Socket socketParaAutenticar = socketServidorAutenticacao.accept();

						DadoAutenticacao dadosAutenticar = recuperarDadosAutenticacao(socketParaAutenticar);
						if (dadosAutenticar != null) {
							boolean usuarioAutenticado = autenticarUsuario(dadosAutenticar);

							ObjectOutputStream fluxoSaidaDados = new ObjectOutputStream(
									socketParaAutenticar.getOutputStream());

							fluxoSaidaDados.writeObject(usuarioAutenticado);
						}
					}
				} catch (IOException e) {
					new Exception(e);
				}
			}
		}.start();
	}

	private boolean autenticarUsuario(DadoAutenticacao dadosAutenticar) {
		return true;
	}

	private DadoAutenticacao recuperarDadosAutenticacao(Socket socketParaAutenticar) {

		ObjectInputStream fluxoEntradaDados;

		try {
			fluxoEntradaDados = new ObjectInputStream(socketParaAutenticar.getInputStream());
			DadoAutenticacao dadoParaAutenticar = (DadoAutenticacao) fluxoEntradaDados.readObject();

			return dadoParaAutenticar;
		} catch (IOException | ClassNotFoundException e) {

			e.printStackTrace();
			return null;
		}
	}
	
	public static ServidorAutenticacao getInstance() {

		if (instancia == null)
			return instancia = new ServidorAutenticacao();

		return instancia;
	}
}
