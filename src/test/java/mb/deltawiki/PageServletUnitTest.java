package mb.deltawiki;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

import mb.deltawiki.PageServlet;
import mb.deltawiki.model.Model;
import mb.deltawiki.model.Model.PageDoesntExistException;
import mockit.Expectations;
import mockit.Mocked;
import mockit.internal.expectations.transformation.ExpectationsTransformer;

public class PageServletUnitTest {

    @Mocked
    Model model;

    PageServlet page;

    @Before
    public void setUp() {
        page = new PageServlet();
    }

    /**
     * Retrieves an existing page from the database at /api/page/somePage.
     * Expects a HTTP 200 response and content = "some content".
     */
    @Test
    public void getExistingPageGetsContentOK() throws PageDoesntExistException {
        final String pageContent = "some content";

        new Expectations() {
            {
                model.getPage("somePage");
                result = pageContent;
            }
        };

        Response result = page.getPage("somePage");

        assertThat(result.getStatus(), is(200));
        assertThat(result.getEntity(), is(pageContent));
    }

    /**
     * Retrieves a nonexistent page from the database at
     * /api/page/nonExistingPage. Expects a HTTP 404 response.
     * 
     * @throws PageDoesntExistException
     */
    @Test
    public void gettingNonExistingPageGets404Error() throws PageDoesntExistException {
        new Expectations() {
            {
                model.getPage("nonExistingPage");
                result = new PageDoesntExistException();
            }
        };

        Response result = page.getPage("nonExistingPage");
        assertThat(result.getStatus(), is(404));
    }

    /**
     * Attempts GET without page name, e.g. GET /api/page/; expects HTTP 400
     * response.
     * 
     * @throws Exception
     */
    @Test
    public void gettingWithoutPageNameGets400Error() throws Exception {
        Response result = page.getPage("");
        assertThat(result.getStatus(), is(400));
    }

}
