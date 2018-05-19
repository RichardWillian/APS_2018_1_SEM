package ecochat.dao.modelos.usuario;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

//import hiberutil.HibernateUtil;
import ecochat.entidades.modelos.Usuario;
import ecochat.hibernate.util.HibernateUtil;

public class UsuarioDAO {

	/**
	 * Adiciona um novo usu�rio
	 * @param nome primeiro nome do usu�rio
	 * @param sobrenome �ltimo nome (sobrenome) do usu�rio
	 */
		public static void addUsuario(String nome, String email) {

			Transaction transacao = null;
			Session session = HibernateUtil.getSessionFactory().openSession();
			try {
				transacao = session.beginTransaction();

				Usuario usr = new Usuario();
				usr.setNome(nome);
				usr.setEmail(email);

				session.save(usr);

				session.getTransaction().commit();
				
			}
			catch (RuntimeException e) {
				if (transacao != null) transacao.rollback();
				e.printStackTrace();
			}
			finally {
				session.flush();
				session.close();
			}
		}
		
		/**
		 * Modifica o sobrenome de um usu�rio, identificado pelo seu ID
		 * @param id c�digo do usu�rio a ser alterado
		 * @param sobrenome novo sobrenome a ser atribu�do ao usu�rio
		 */
		public static int updateSobrenome(int id, String sobrenome) {
			int qtdeRegAtualizados = 0;
			Transaction trns = null;
			Session session = HibernateUtil.getSessionFactory().openSession();
			try {
				trns = session.beginTransaction();
				String hqlUpdate = "update Usuario u set u.sobrenome = :novoSobrenome where u.id = :antigoId";
				qtdeRegAtualizados = session.createQuery(hqlUpdate)
						.setString("novoSobrenome", sobrenome).setInteger("antigoId", id)
						.executeUpdate();
				trns.commit();
			}
			catch (RuntimeException e) {
				if (trns != null) trns.rollback();
				e.printStackTrace();
			}
			finally {
				session.flush();
				session.close();
			}
			return qtdeRegAtualizados;
		}

		/**
		 * Atualiza as informa��es de um usu�rio
		 * @param user inst�ncia de usu�rio a ser modificada
		 */
		public static void updateUsuario(Usuario user) {
			Transaction trns = null;
			Session session = HibernateUtil.getSessionFactory().openSession();
			try {
				trns = session.beginTransaction();

				session.update(user);

				session.getTransaction().commit();
			}
			catch (RuntimeException e) {
				if (trns != null) trns.rollback();
				e.printStackTrace();
			}
			finally {
				session.flush();
				session.close();
			}
		}

		/**
		 * Recupera o nome completo de um usu�rio, pesquisando o banco com base no seu primeiro nome
		 * @param primeiroNome nome parcial do usu�rio, sem o seu sobrenome.
		 */
		public static void getNomeCompleto(String primeiroNome) {
			Transaction trns = null;
			Session session = HibernateUtil.getSessionFactory().openSession();
			try {
				trns = session.beginTransaction();
				
				@SuppressWarnings("unchecked")
				List<Usuario> users = session
						.createQuery("from Usuario as u where u.nome = :firstName")
						.setString("firstName", primeiroNome).list();
				for (Iterator<Usuario> iter = users.iterator(); iter.hasNext();) {
					Usuario user = iter.next();
					System.out.println(user.getNome() + " " + user.getEmail());
				}
				
				trns.commit();
			}
			catch (RuntimeException e) {
				if (trns != null) trns.rollback();
				e.printStackTrace();
			}
			finally {
				session.flush();
				session.close();
			}
		}

		/**
		 * Remove um usu�rio da base de dados
		 * @param user usu�rio a ser removido
		 */
		public static void deleteUsuario(Usuario user) {
			Transaction trns = null;
			Session session = HibernateUtil.getSessionFactory().openSession();
			try {
				trns = session.beginTransaction();
				session.delete(user);
				session.getTransaction().commit();
			}
			catch (RuntimeException e) {
				if (trns != null) trns.rollback();
				e.printStackTrace();
			}
			finally {
				session.flush();
				session.close();
			}
		}

}
