package edu.columbia.ase.teamproject.persistence.dao.generic;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Interface GenericDao.
 *
 * @param <E> the element type
 * @param <K> the key type
 */
public interface GenericDao<E, K> {

	 /**
 	 * Adds the.
 	 *
 	 * @param entity the entity
 	 * @return the e
 	 */
 	E add(E entity);
	 
	 /**
 	 * Update.
 	 *
 	 * @param entity the entity
 	 * @return the e
 	 */
 	E update(E entity);
	 
	 /**
 	 * Removes the.
 	 *
 	 * @param entity the entity
 	 */
 	void remove(E entity);
	 
	 /**
 	 * Find.
 	 *
 	 * @param key the key
 	 * @return the e
 	 */
 	E find(K key);
	 
	 /**
 	 * List.
 	 *
 	 * @return the list
 	 */
 	List<E> list();
	 
	 /**
 	 * Flush.
 	 */
 	void flush();
 
}
