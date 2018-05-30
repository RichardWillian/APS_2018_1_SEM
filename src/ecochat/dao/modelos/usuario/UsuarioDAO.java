package ecochat.dao.modelos.usuario;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import ecochat.entidades.modelos.Usuario;
import ecochat.hibernate.util.HibernateUtil;

public class UsuarioDAO {

	/**
	 * Adiciona um novo usu�rio
	 * 
	 * @param nome
	 *            primeiro nome do usu�rio
	 * @param sobrenome
	 *            �ltimo nome (sobrenome) do usu�rio
	 */
	public static void addUsuario(String nome, String email, String senha) {

		Transaction transacao = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			transacao = session.beginTransaction();

			Usuario usr = new Usuario();
			usr.setNome(nome);
			usr.setEmail(email);
			usr.setSenha(senha);

			session.save(usr);
			session.getTransaction().commit();

		} catch (RuntimeException e) {
			if (transacao != null)
				transacao.rollback();
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
	}

	/**
	 * Atualiza as informa��es de um usu�rio
	 * 
	 * @param user
	 *            inst�ncia de usu�rio a ser modificada
	 */
	public static void updateUsuario(Usuario user) {
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();

			session.update(user);

			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (trns != null)
				trns.rollback();
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
	}

	/**
	 * Remove um usu�rio da base de dados
	 * 
	 * @param user
	 *            usu�rio a ser removido
	 */
	public static void deleteUsuario(Usuario user) {
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			session.delete(user);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (trns != null)
				trns.rollback();
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
	}
	
	public Usuario getUsuarioPorCamposAutenticacao(String nome, String email, String senha) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria criteria = session.createCriteria(Usuario.class);
			
			if(nome != null)
				criteria.add(Restrictions.eq("nome", nome));
			if(email != null)
				criteria.add(Restrictions.eq("email", email));
			
			criteria.add(Restrictions.eq("senha", senha));
			
			Usuario usuario = (Usuario) criteria.uniqueResult();
			
			return usuario;
		} finally {
			session.flush();
			session.close();
		}
	}
}
