package ecochat.hibernate.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Esta classe encapsula as opera��es b�sicas que envolvem o Hibernate
 * quanto a inicializa��o, cria��o de sess�es e encerramento.
 * 
 * @author Professor Leandro Fernandes
 */
public class HibernateUtil {
	
	// Refer�ncia para a f�brica de sess�es (do padr�o Singleton)
	private static SessionFactory sessionFactory = null;
	
	private HibernateUtil(){
		Logger.getLogger("org.hibernate").setLevel(Level.OFF);
	}

	// M�todo para inicializa��o e cria��o de uma f�brica de sess�es
	private static SessionFactory buildSessionFactory() {
		try {
			// Cria uma SessionFactory a partir do arquivo hibernate.cfg.xml
			return new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			// Garantindo o registro da exce��o assim como sua exibi��o na console
			System.err.println("Falha na cria��o da SessionFactory inicial.\n" + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * Retorna a f�brica de sess�es 
	 * @return uma f�brica de sess�es
	 */
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null)
			sessionFactory = buildSessionFactory();
		return sessionFactory;
	}
	
	/**
	 * Reescrita que garante o encerramento da f�brica de sess�es juntamente
	 * com os demais componentes do framework. 
	 */
	@Override
	protected void finalize() throws Throwable {
        sessionFactory.close();
		super.finalize();
	}
}