package mb.deltawiki;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import mb.deltawiki.PageServlet;

public class PageServletEndpointTest extends JerseyTest {

    // TODO GET, PUT, DELETE at /api/page

    /*
     * TODO GET /api/page/<p> retrieves an existing page and responds with HTTP
     * 200, with JSON {pageName: '<p>', pageContent: '<content>'}. Log INFO 'GET
     * /api/page/<p>: 200 Accessed page <p>'.
     */

    @Override
    protected Application configure() {
        return new ResourceConfig(PageServlet.class);
    }

    private Builder createRequestHelper() {
        return target("api/page").request();
    }

    /*
     * TODO GET /api/page/<p> attempts to retrieve a nonexisting page; server
     * responds with HTTP 404. Log WARNING 'GET /api/page/<p>: 404 Page <p> not
     * found.
     */

    /*
     * TODO GET /api/page/ without parameter; server responds HTTP 400 Log
     * WARNING 'GET /api/page/: 400 GET requested without parameter.
     */

    /*
     * TODO PUT /api/page/<p> attempts to create a new page; server responds
     * HTTP 201 Log INFO 'PUT /api/page/<p>: 201 Page <p> created.
     */

    /*
     * TODO PUT /api/page/<p>, where <p> exists; server updates <p> and responds
     * HTTP 200. Log INFO 'PUT /api/page/<p>: 200 Page <p> updated.
     */

    /*
     * TODO HEAD /api/page/<p>, where <p> exists; server responds HTTP 200 with
     * only headers. Log INFO 'HEAD /api/page/<p>: 200 Accessed page <p>.
     */


    /*
     * TODO DELETE /api/page<p> where <p> exists; server responds 200 and
     * deletes page. Log INFO 'DELETE /api/page<p>: 200 Deleted page <p>.'
     */

    /*
     * TODO DELETE /api/page<p> where <p> doesn't exist; server responds 400
     * with 'Page <p> doesn't exist'. Log ERROR 'DELETE /api/page/<p>: 400 Could
     * not delete page <p>; doesn't exist.'
     */

}
