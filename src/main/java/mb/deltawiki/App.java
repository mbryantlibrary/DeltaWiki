package mb.deltawiki;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

	final static int PORT_NUMBER = 8888;
	
	private static Logger LOG = LoggerFactory.getLogger(App.class);
	
    private App() {
    }

    public static void main(String[] args) throws Exception {

    	LOG.info("Starting server on port {}",PORT_NUMBER);
    	
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(PORT_NUMBER).build();
        ResourceConfig config = new ResourceConfig(PageServlet.class);

        Server server = JettyHttpContainerFactory.createServer(baseUri, config);

    }
}
