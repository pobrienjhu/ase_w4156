package edu.columbia.ase.teamproject.persistence.dao.generic;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.OptimisticLockException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.columbia.ase.teamproject.persistence.dao.util.InputValidatorTruncator;

// TODO: Auto-generated Javadoc
/**
 * Basic DAO operations dependent with Hibernate's specific classes.
 *
 * @param <E> the element type
 * @param <K> the key type
 * @see SessionFactory
 */
@Transactional(propagation= Propagation.REQUIRED, readOnly=false)
public class HibernateDao<E, K extends Serializable> implements GenericDao<E, K> {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(HibernateDao.class);
	
    /** The session factory. */
    private SessionFactory sessionFactory;
    
    /** The dao type. */
    protected Class<? extends E> daoType;
    
    /** The input validator truncator. */
    protected InputValidatorTruncator inputValidatorTruncator = new InputValidatorTruncator();

    /**
     * Instantiates a new hibernate dao.
     */
    @SuppressWarnings("unchecked")
	public HibernateDao() {
        //daoType = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass())
        //                .getActualTypeArguments()[0];
        
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        Type type = genericSuperclass.getActualTypeArguments()[0];
        
        if (type instanceof Class) {
          this.daoType = (Class<E>) type;
        } else if (type instanceof ParameterizedType) {
          this.daoType = (Class<E>) ((ParameterizedType)type).getRawType();
        }
        
    }

    /**
     * Sets the session factory.
     *
     * @param sessionFactory the new session factory
     */
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Current session.
     *
     * @return the session
     */
    protected Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    /* (non-Javadoc)
     * @see edu.columbia.ase.teamproject.persistence.dao.generic.GenericDao#add(java.lang.Object)
     */
    @Override
    public E add(E entity) {
    	entity = truncateFreeTextFields(entity);
    	entity = (E) currentSession().merge(entity);
        currentSession().save(entity);
        return entity;
    }

    /* (non-Javadoc)
     * @see edu.columbia.ase.teamproject.persistence.dao.generic.GenericDao#update(java.lang.Object)
     */
    @Override
    public E update(E entity) throws OptimisticLockException{
    	entity = truncateFreeTextFields(entity);
    	return (E) currentSession().merge(entity);
        //currentSession().saveOrUpdate(entity);
    }

    /* (non-Javadoc)
     * @see edu.columbia.ase.teamproject.persistence.dao.generic.GenericDao#remove(java.lang.Object)
     */
    @Override
    public void remove(E entity) throws OptimisticLockException {
    	entity = (E) currentSession().merge(entity);
        currentSession().delete(entity);
    }

    /* (non-Javadoc)
     * @see edu.columbia.ase.teamproject.persistence.dao.generic.GenericDao#find(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
	@Override
    public E find(K key) {
        return (E) currentSession().get(daoType, key);
    }

    /* (non-Javadoc)
     * @see edu.columbia.ase.teamproject.persistence.dao.generic.GenericDao#list()
     */
    @SuppressWarnings("unchecked")
	@Override
    public List<E> list() {
        return currentSession().createCriteria(daoType).list();
    }
    
    /**
     * Truncate free text fields.
     *
     * @param e the e
     * @return the e
     */
    private E truncateFreeTextFields(E e) {
        try {
            e = inputValidatorTruncator.truncate(e);
        } catch (Exception ex) {
            logger.error("Problem truncating entity ["+e+"]",ex);
        }
        return e;
    }
    
    /* (non-Javadoc)
     * @see edu.columbia.ase.teamproject.persistence.dao.generic.GenericDao#flush()
     */
    public void flush() {
    	currentSession().flush();
    }
    
}
