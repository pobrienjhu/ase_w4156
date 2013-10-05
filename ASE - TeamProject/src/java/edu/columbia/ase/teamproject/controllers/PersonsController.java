package edu.columbia.ase.teamproject.controllers;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.columbia.ase.teamproject.persistence.dao.PersonsDao;
import edu.columbia.ase.teamproject.persistence.domain.Persons;

@Controller
@RequestMapping("/persons.do")
public class PersonsController {

	private Logger log = LoggerFactory.getLogger(PersonsController.class);

	@Autowired
	private PersonsDao personsDao;	

	@RequestMapping(method = RequestMethod.GET)
	public String printPersons(ModelMap model) {
		 log.info("Servlet request recieved");
		
		 List<Persons> personsList = personsDao.list();
		 
		 log.info("Found list of persons with size |"+personsList.size()+"|");
		 
	     StringBuilder personsSb = new StringBuilder("");
	     
		 for(Persons person: personsList ){
			 personsSb.append("<h1>"+person+"</h1>");
		 }
	     
	     model.addAttribute("message", personsSb.toString());

		 return "PersonsList";
    }


	public PersonsDao getPersonsDao() {
		return personsDao;
	}

	public void setPersonsDao(PersonsDao personsDao) {
		log.info("setting personDao");
		this.personsDao = personsDao;
	}
}
