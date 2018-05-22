package ecochat.aplicacoes.servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import ecochat.dao.modelos.usuario.UsuarioDAO;
import ecochat.entidades.DadoAutenticacao;
import ecochat.entidades.modelos.Usuario;

public class ServidorAutenticacao {

	private static ServerSocket socketServidorAutenticacao;
	private static ServidorAutenticacao instancia;

	public static ServidorAutenticacao getInstance() {

		if (instancia == null)
			return instancia = new ServidorAutenticacao();

		return instancia;
	}

	public void iniciarServidor() {

		new Thread() {
			public void run() {

				try {
					socketServidorAutenticacao = new ServerSocket(12346, 20, InetAddress.getByName("127.255.255.254"));

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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}

	private boolean autenticarUsuario(DadoAutenticacao dadosAutenticar) {

		UsuarioDAO usuarioDAO = new UsuarioDAO();

		String nome = dadosAutenticar.getNome();
		String email = dadosAutenticar.getEmail();
		String senha = dadosAutenticar.getSenha();

		Usuario usuario = usuarioDAO.getUsuarioPorCamposAutenticacao(nome, email, senha);

		if (usuario == null)
			return false;

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
}
