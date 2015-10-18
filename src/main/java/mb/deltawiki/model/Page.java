package mb.deltawiki.model;

import org.bson.Document;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Page {

	public String pageName;
	public String pageContent;
	
	@JsonCreator
	public
	Page(@JsonProperty("pageName") String pageName, @JsonProperty("pageContent") String pageContent) {
		this.pageName = pageName;
		this.pageContent = pageContent;
	}
	
	public Page(Document page) {
		this(page.getString("pageName"),page.getString("pageContent"));
	}

	public Document asDocument() {
		return new Document().append("pageName", pageName).append("pageContent", pageContent);
	}

	public String toJSON() {
		return String.format("{pageName:\"%s\",pageContent:\"%s\"}", pageName,pageContent);
	}
}
