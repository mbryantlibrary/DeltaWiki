package mb.DeltaWiki;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class App {
	
	public static void main(String[] args) throws Exception
    {
		
		URI baseUri = UriBuilder.fromUri("http://localhost/").port(8888).build();
		ResourceConfig config = new ResourceConfig(PageServlet.class);
		
		Server server = JettyHttpContainerFactory.createServer(baseUri, config);
		
    }
}
