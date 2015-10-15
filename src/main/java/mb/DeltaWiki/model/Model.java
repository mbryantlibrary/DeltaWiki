package mb.DeltaWiki.model;

public class Model {

	public String getPage(String string) throws PageDoesntExistException {
		return string;
	}

	public static class PageDoesntExistException extends Throwable {

	}
}
