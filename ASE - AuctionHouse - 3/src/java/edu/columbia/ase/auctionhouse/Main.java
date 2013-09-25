package edu.columbia.ase.auctionhouse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        Main p = new Main();
        p.start(args);
	}


	    private void start(String[] args) {
	        ApplicationContext context = 
	            new ClassPathXmlApplicationContext("config/spring-config.xml");
	    }
	
}
