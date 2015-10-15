package mb.deltawiki;

import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import mb.deltawiki.model.Model;
import mb.deltawiki.model.Model.PageDoesntExistException;

@Path("/api/page/{pageName}")
public class PageServlet {

    private Model model;
    private static Logger LOG = Logger.getLogger(PageServlet.class.getName());

    public PageServlet() {
        model = new Model();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getPage(@PathParam("pageName") String pageName) {
        if (pageName.isEmpty()) {
            // guard against empty page names
            return Response.status(400).entity("GET requested without parameter").build();
        }
        try {
            // try and get page from database
            String result = model.getPage(pageName);

            // success!
            return Response.ok(result).build();
        } catch (PageDoesntExistException ex) {
            // couldn't find page, oh well, 404
            
            LOG.warning(String.format("GET /api/page/%1$s: Page %1$s was not found", pageName));
            
            return Response.status(404).build();
        }
    }

    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    public String putPage() {
        return "Test";
    }

}
