package mb.deltawiki.model;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

public class PageTest {

	@Test
	public void testSerialiseToJSON() {
		String expected = "{pageName:\"testPage\",pageContent:\"someContent\"}";
		
		Page page = new Page("testPage", "someContent");
		
		assertThat(page.toJSON(),is(expected));
	}

}
