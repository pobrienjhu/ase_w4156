package edu.columbia.ase.teamproject.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.ModelMap;

@Controller
@RequestMapping("/hello")
public class HelloController{
	
	
	private Logger log = LoggerFactory.getLogger(HelloController.class);
	
 
   @RequestMapping(method = RequestMethod.GET)
   public String printHello(ModelMap model) {
	   
	  log.info("MVC Get request recieved");
	   
      model.addAttribute("message", "Hello Spring MVC Framework!");

      return "HelloWorld";
   }

}
