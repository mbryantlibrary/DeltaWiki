package mb.deltawiki.model;

import static com.mongodb.client.model.Filters.eq;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.bson.Document;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.fakemongo.Fongo;
import com.github.fakemongo.junit.FongoRule;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import mb.deltawiki.model.Model.PageAlreadyExistsException;
import mb.deltawiki.model.Model.PageDoesntExistException;

public class MongoModelTest {
	
	@Rule
	public FongoRule fongoRule = new FongoRule();

	MongoClient mongoClient;
	MongoModel model;
	MongoCollection<Document> pages;

	Page existingPage = new Page("existingPage","someExistingContent");
	
	@Before
	public void setUp() {
		mongoClient = new Fongo("testdb").getMongo();
		model = MongoModel.injectMongo(mongoClient);
		MongoDatabase db = mongoClient.getDatabase("testdb");
		pages = db.getCollection("pages");
		pages.insertOne(existingPage.asDocument());

		model.connect("testdb");
	}

	/**
	 * tries to get a page that doesn't exist; mongo will return a null object but this
	 * should be thrown as an exception
	 */
	@Test(expected=PageDoesntExistException.class)
	public void gettingNonExistentPageShouldThrowPageDoesntExistExc() throws Exception {
		model.getPage("nonExistentPage");
		
	}
	
	/**
	 * Tries to get an existing page; 
	 */
	@Test
	public void getsPage() throws Exception {
		
		Page page = model.getPage("existingPage");
		assertThat(page.pageContent, is("someExistingContent"));
	}
	
	/**
	 * Tests that pageExists(existingPage)==true and pageExists(nonExistentPage)==false
	 */
	@Test
	public void testPageExists() throws Exception {
		assertTrue(model.exists("existingPage"));
		assertFalse(model.exists("nonExistentPage"));
	}
	
	/**
	 * Tests creation of a page and checks that it exists in the database.
	 */
	@Test
	public void testCreatePage() throws Exception {
		Page testPage = new Page("TestPage", "some content");
		
		model.create(testPage);
		
		assertThat(pages.count(eq("pageName", "TestPage")),is(equalTo(1L)));
		assertThat(pages.find(eq("pageName", "TestPage")).limit(1).first().getString("pageContent"),is("some content"));
	}
	
	/**
	 * Attempts to create a page with an existing page name; should throw a PageAlreadyExists exception.
	 */
	@Test(expected = PageAlreadyExistsException.class)
	public void createThrowsErrorIfPageExists() throws Exception {
		model.create(existingPage);
	}
	
	/**
	 * Updates page content.
	 */
	@Test
	public void updateUpdatesPage() throws Exception {
		existingPage.pageContent = "updated content";
		model.update(existingPage);
		
		assertThat(pages.count(eq("pageName", "existingPage")),is(1L));
		
		Document docFromDB = pages.find(eq("pageName", "existingPage")).limit(1).first();

		assertThat(docFromDB.getString("pageContent"),is("updated content"));
		
	}
	
	/**
	 * Tests removal of a page.
	 */
	@Test
	public void deleteRemovesPage() throws Exception {
		assertThat(pages.count(eq("pageName", "existingPage")),is(1L));
		
		model.delete("existingPage");

		assertThat(pages.count(eq("pageName", "existingPage")),is(0L));
		
	}

}
