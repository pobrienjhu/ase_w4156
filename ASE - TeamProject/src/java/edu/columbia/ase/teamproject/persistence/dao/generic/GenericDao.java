package edu.columbia.ase.teamproject.persistence.dao.generic;

import java.util.List;

public interface GenericDao<E, K> {

	 E add(E entity);
	 
	 E update(E entity);
	 
	 void remove(E entity);
	 
	 E find(K key);
	 
	 List<E> list();
 
}
