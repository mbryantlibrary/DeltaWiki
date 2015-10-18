package mb.deltawiki;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import mb.deltawiki.model.Model.PageDoesntExistException;
import mb.deltawiki.model.MongoModel;
import mb.deltawiki.model.Page;
import mockit.Expectations;
import mockit.Mocked;

public class PageServletUnitTest {

    @Mocked
    MongoModel model;

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
    	final Page testpage = new Page("somePage", "some content");

        new Expectations() {
            {
                model.getPage("somePage");
                result = testpage;
            }
        };

        Response result = page.get("somePage");

        assertThat(result.getStatus(), is(200));
        assertThat(result.getEntity(), is(testpage.toJSON()));
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

        Response result = page.get("nonExistingPage");
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
        Response result = page.get("");
        assertThat(result.getStatus(), is(400));
    }

    /**
     * Attempts a PUT on a nonexistent page; expects HTTP 201 (created)
     * 
     * @throws Exception
     */
    @Test
    public void putCreatesNewPage() throws Exception {
        new Expectations() {
            {
                model.exists("nonExistingPage");
                result = false;
            }
        };
        Response result = page.put("nonExistingPage");

        assertThat(result.getStatus(), is(201));

    }

    /**
     * Attempts a PUT on an existing page; expects HTTP 200
     * 
     * @throws Exception
     */
    @Test
    public void putUpdatesPage() throws Exception {
        new Expectations() {
            {
                model.exists("nonExistingPage");
                result = true;
            }
        };
        Response result = page.put("nonExistingPage");

        assertThat(result.getStatus(), is(200));

    }

    /**
     * requests headers for an existing page, expects HTTP 200
     * 
     * @throws Exception
     */
    @Test
    public void headGets200IfPageExists() throws Exception {
        final String pageContent = "some content";

        new Expectations() {
            {
                model.exists("somePage");
                result = true;
            }
        };

        Response result = page.head("somePage");

        assertThat(result.getStatus(), is(200));
    }

    /**
     * requests headers for an existing page, expects HTTP 200
     * 
     * @throws Exception
     */
    @Test
    public void headGets404IfPageNonExistent() throws Exception {
        final String pageContent = "some content";

        new Expectations() {
            {
                model.exists("somePage");
                result = false;
            }
        };

        Response result = page.head("somePage");

        assertThat(result.getStatus(), is(404));
    }

    /**
     * deletes a page, if it exists; expects HTTP 200
     * @throws Exception
     */
    @Test
    public void deleteDeletesPageIfExisting() throws Exception {
        new Expectations() {
            {
                model.exists("somePage");
                result = true;
            }
        };

        Response result = page.delete("somePage");

        assertThat(result.getStatus(), is(200));
    }
    

    /**
     * tries to delete a nonexistent page; expects HTTP 404
     * @throws Exception
     */
    @Test
    public void deleteGets404IfPageDoesntExist() throws Exception {
        new Expectations() {
            {
                model.exists("somePage");
                result = false;
            }
        };

        Response result = page.delete("somePage");

        assertThat(result.getStatus(), is(404));
    }
}
