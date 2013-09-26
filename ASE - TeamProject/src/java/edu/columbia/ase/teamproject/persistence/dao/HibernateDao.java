package edu.columbia.ase.teamproject.persistence.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Basic DAO operations dependent with Hibernate's specific classes
 * @see SessionFactory
 */
@Transactional(propagation= Propagation.REQUIRED, readOnly=false)
public class HibernateDao<E, K extends Serializable> implements GenericDao<E, K> {

    private SessionFactory sessionFactory;
    protected Class<? extends E> daoType;

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
    public void add(E entity) {
        currentSession().save(entity);
    }

    @Override
    public void update(E entity) {
        currentSession().saveOrUpdate(entity);
    }

    @Override
    public void remove(E entity) {
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
}
