package ecochat.aplicacoes.servidor;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorAutenticacao {

	private static ServerSocket socketServidorAutenticacao;
	private static ServidorAutenticacao instancia;
	
	public static ServidorAutenticacao getInstance() {

		if (instancia == null)
			return instancia = new ServidorAutenticacao();

		return instancia;
	}

	public void iniciarServidor() {
		
		try {
			socketServidorAutenticacao = new ServerSocket(12345, 20, InetAddress.getByName("127.0.0.1"));
			Socket socket = socketServidorAutenticacao.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
