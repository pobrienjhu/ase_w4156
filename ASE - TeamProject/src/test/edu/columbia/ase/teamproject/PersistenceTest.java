package edu.columbia.ase.teamproject;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import edu.columbia.ase.teamproject.persistence.dao.HibernateDao;
import edu.columbia.ase.teamproject.persistence.dao.PersonsDao;
import edu.columbia.ase.teamproject.persistence.dao.impl.PersonsDaoImpl;
import edu.columbia.ase.teamproject.persistence.domain.Persons;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class PersistenceTest extends AbstractJUnit4SpringContextTests{

	 @Autowired
	 private SessionFactory sessionFactory;
	 
	 @Autowired
	 private PersonsDao personsDao;

	 @Test
	 public void testHibernateConfiguration() {
	  // Did Spring container instantiated and prepared sessionFactory
	  assertNotNull (sessionFactory); 
	 }
	 
	 @Test
	 public void testPersonsDao(){
		  // Did Spring container instantiated and prepared personsDao
		  assertNotNull (personsDao); 
	 }
	 
	 @Test
	 public void testPersonsDaoInsert(){
		 // Is personsDao ready for operations	 
		 Persons patrick = new Persons("OBrien", "Patrick",  "123", "NY", "po22207@columbia.edu");
		 personsDao.add(patrick);
		 
	 }
	 
	 
	 @Test
	 public void testPersonsDaoQuery(){
		// Is personsDao ready for operations	 
		 List<Persons> personsList = personsDao.list();
		 
		 System.out.println("Looking for list of all persons in the db...");
		 
		 //personsList.size()
		 assertNotNull(personsList);
		 assertFalse( personsList.size() == 0 ) ;
		 
		 for(Persons person: personsList ){
			 System.out.println(person);
		 }
		 
	 }
	
}
