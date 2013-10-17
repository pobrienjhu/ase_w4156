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

/**
 * Basic DAO operations dependent with Hibernate's specific classes
 * @see SessionFactory
 */
@Transactional(propagation= Propagation.REQUIRED, readOnly=false)
public class HibernateDao<E, K extends Serializable> implements GenericDao<E, K> {

	private static final Logger logger = LoggerFactory.getLogger(HibernateDao.class);
	
    private SessionFactory sessionFactory;
    protected Class<? extends E> daoType;
    
    protected InputValidatorTruncator inputValidatorTruncator = new InputValidatorTruncator();

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

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public E add(E entity) {
    	entity = truncateFreeTextFields(entity);
    	entity = (E) currentSession().merge(entity);
        currentSession().save(entity);
        return entity;
    }

    @Override
    public E update(E entity) throws OptimisticLockException{
    	entity = truncateFreeTextFields(entity);
    	return (E) currentSession().merge(entity);
        //currentSession().saveOrUpdate(entity);
    }

    @Override
    public void remove(E entity) throws OptimisticLockException {
    	entity = (E) currentSession().merge(entity);
        currentSession().delete(entity);
    }

    @SuppressWarnings("unchecked")
	@Override
    public E find(K key) {
        return (E) currentSession().get(daoType, key);
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<E> list() {
        return currentSession().createCriteria(daoType).list();
    }
    
    private E truncateFreeTextFields(E e) {
        try {
            e = inputValidatorTruncator.truncate(e);
        } catch (Exception ex) {
            logger.error("Problem truncating entity ["+e+"]",ex);
        }
        return e;
    }
    
    public void flush() {
    	currentSession().flush();
    }
    
}
