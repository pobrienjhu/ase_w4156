package edu.columbia.ase.teamproject.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloServlet extends HttpServlet
{

	private Logger log = LoggerFactory.getLogger(HelloServlet.class);
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -1471602712363458893L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
		log.info("Servlet request recieved");
		
        resp.setContentType("text/html");
        resp.getWriter().println("<h1>Hello World</h1>");
    }

}
