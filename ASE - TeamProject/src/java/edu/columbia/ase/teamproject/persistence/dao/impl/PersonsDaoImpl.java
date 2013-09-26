package edu.columbia.ase.teamproject.persistence.dao.impl;


import java.util.List;

import org.hibernate.Criteria;

import edu.columbia.ase.teamproject.persistence.dao.HibernateDao;
import edu.columbia.ase.teamproject.persistence.dao.PersonsDao;
import edu.columbia.ase.teamproject.persistence.domain.Persons;

public class PersonsDaoImpl extends HibernateDao<Persons, Long> implements PersonsDao {


    @SuppressWarnings("unchecked")
	@Override
    public List<Persons> list() {
        return currentSession().createCriteria(Persons.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }

}
