package ecochat.hibernate.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Esta classe encapsula as operações básicas que envolvem o Hibernate
 * quanto a inicialização, criação de sessões e encerramento.
 * 
 * @author Professor Leandro Fernandes
 */
public class HibernateUtil {
	
	// Referência para a fábrica de sessões (do padrão Singleton)
	private static SessionFactory sessionFactory = null;
	
	private HibernateUtil(){
		Logger.getLogger("org.hibernate").setLevel(Level.OFF);
	}

	// Método para inicialização e criação de uma fábrica de sessões
	private static SessionFactory buildSessionFactory() {
		try {
			// Cria uma SessionFactory a partir do arquivo hibernate.cfg.xml
			return new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			// Garantindo o registro da exceção assim como sua exibição na console
			System.err.println("Falha na criação da SessionFactory inicial.\n" + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * Retorna a fábrica de sessões 
	 * @return uma fábrica de sessões
	 */
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null)
			sessionFactory = buildSessionFactory();
		return sessionFactory;
	}
	
	/**
	 * Reescrita que garante o encerramento da fábrica de sessões juntamente
	 * com os demais componentes do framework. 
	 */
	@Override
	protected void finalize() throws Throwable {
        sessionFactory.close();
		super.finalize();
	}
}