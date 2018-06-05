package ecochat.aplicacoes.servidor;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;

import ecochat.entidades.DadoCompartilhado;
import ecochat.entidades.DadoCompartilhadoServidor;
import ecochat.interfaces.telas.UIJanelaServidorCentral;
import ecochat.utilitarios.ConstantesGerais;

public class ServidorCentral {

	private static ServerSocket socketServidorCentral;
	private static List<Socket> socketsConectados;
	private static ServidorCentral instancia;
	public Socket cliente;

	public static void main(String[] args) {

		try {
			UIJanelaServidorCentral.getInstance();
		} catch (HibernateException exception) {
			exception.printStackTrace();
		} catch (Exception e) {
			System.err.println("Ops! " + e.getMessage() + "\n");
		}
	}

	public void iniciarServidor() {

		try {
			ServidorAutenticacao.getInstance().iniciarServidor();
			ServidorChat.getInstance().iniciarServidor();

			socketServidorCentral = new ServerSocket(ConstantesGerais.PORTA_SERVIDOR_CENTRAL,
					ConstantesGerais.QUANTIDADE_MAXIMA_CONECTADOS,
					InetAddress.getByName(ConstantesGerais.IP_SERVIDOR_CENTRAL));

			socketsConectados = new ArrayList<Socket>();
			UIJanelaServidorCentral.getInstance().mostrarMensagem("        ---===== Servidor Conectado =====---");
			while (true) {

				Socket socket = socketServidorCentral.accept();
				UIJanelaServidorCentral.getInstance().mostrarConectados(socket.getInetAddress().getHostAddress());

				socketsConectados.add(socket);

				atualizarUsuariosOnlines(socket.getInetAddress().getHostAddress());
			}
		} catch (IOException ioE) {
			System.err.println(ioE.getMessage());
		}
	}
	
	public void notificarUsuario(final DadoCompartilhado dadoCompartilhado) {
		new Thread() {
			public void run() {
				
				for(Socket socketConectado : socketsConectados){
					String ipSocketConectado = socketConectado.getInetAddress().getHostAddress();
					if(ipSocketConectado.equals(dadoCompartilhado.getDestinatario())){
						ObjectOutputStream fluxoSaidaDados;
						try {
							fluxoSaidaDados = new ObjectOutputStream(
									socketConectado.getOutputStream());
							
							DadoCompartilhadoServidor dadoCompartilhadoServidor = new DadoCompartilhadoServidor();
							dadoCompartilhadoServidor.setDadoCompartilhado(dadoCompartilhado);
							
							fluxoSaidaDados.writeObject(dadoCompartilhadoServidor);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			};
		}.start();
	}

	public void desligarServidor() {
		try {

			for (Socket socketConectado : socketsConectados) {
				socketConectado.close();
			}

			socketServidorCentral.close();
			UIJanelaServidorCentral.getInstance().mostrarMensagem("     ---===== Servidor Desconectado =====---");

		} catch (IOException ioE) {
			System.err.println("Falha ao desligar o servidor\n\n" + ioE.getMessage());
		}
	}

	public void atualizarUsuariosOnlines(final String ipSocketConectado) {
		new Thread() {
			public void run() {
				List<Socket> socketsConectadosCopia = new ArrayList<Socket>(socketsConectados);
				try {

					Socket socketSeraAtualizado = null;

					for (Socket socketConectado : socketsConectadosCopia) {

						String ipSocketLista = socketConectado.getInetAddress().getHostAddress();
						if (!ipSocketLista.equals(ipSocketConectado)) {
							ObjectOutputStream fluxoSaidaDados = new ObjectOutputStream(
									socketConectado.getOutputStream());
							
							DadoCompartilhadoServidor dadoCompartilhadoServidor = new DadoCompartilhadoServidor();
							dadoCompartilhadoServidor.setIpUsuarioConectou(ipSocketConectado);
							
							fluxoSaidaDados.writeObject(dadoCompartilhadoServidor);
						} else
							socketSeraAtualizado = socketConectado;
					}

					for (Socket socketConectado : socketsConectadosCopia) {

						String ipSocketLista = socketConectado.getInetAddress().getHostAddress();
						if (!ipSocketLista.equals(ipSocketConectado)) {
							ObjectOutputStream fluxoSaidaDados = new ObjectOutputStream(
									socketSeraAtualizado.getOutputStream());
							
							DadoCompartilhadoServidor dadoCompartilhadoServidor = new DadoCompartilhadoServidor();
							dadoCompartilhadoServidor.setIpUsuarioConectou(ipSocketLista);
							
							fluxoSaidaDados.writeObject(dadoCompartilhadoServidor);
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	public static ServidorCentral getInstance() {

		if (instancia == null)
			return instancia = new ServidorCentral();

		return instancia;
	}
}
