package edu.columbia.ase.teamproject;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AnnotationConfigApplicationContext parentContext =
				new AnnotationConfigApplicationContext(
						"edu.columbia.ase.teamproject.config");
        ClassPathXmlApplicationContext context =
	            new ClassPathXmlApplicationContext(parentContext);
        context.setConfigLocation("spring-config-main.xml");
        context.refresh();
	}
	
}
