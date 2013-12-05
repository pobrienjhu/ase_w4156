package edu.columbia.ase.teamproject;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebXmlConfiguration;

// TODO: Auto-generated Javadoc
/**
 * The Class Main.
 */
public class Main {

    /**
     * The main method.
     *
     * @param args
     *            the arguments
     * @throws Exception
     *             the exception
     */
    public static void main(final String[] args) throws Exception {
        final WebAppContext webAppContext = new WebAppContext();
        webAppContext.setResourceBase("src/main/webapp");
        webAppContext.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false");
        webAppContext.setContextPath("/");
        webAppContext.setConfigurations(new Configuration[] { new WebXmlConfiguration() });

        // database gui
        org.hsqldb.util.DatabaseManagerSwing.main(new String[] { "--url",
                "jdbc:hsqldb:mem:dataSource", "--noexit" });

        final Server server = new Server(8080);
        server.setHandler(webAppContext);
        server.start();
        server.join();

    }

}
