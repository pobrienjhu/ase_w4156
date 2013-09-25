package edu.columbia.ase.auctionhouse.customer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

        ApplicationContext context = 
	            new ClassPathXmlApplicationContext("config/customer-spring-config.xml");
        		
	}

}
