package mb.deltawiki.model;

public interface Model {

    public Page getPage(String string) throws PageDoesntExistException;

    public class PageDoesntExistException extends Exception {

    }
    
    public class PageAlreadyExistsException extends RuntimeException {
    	
    }

    public boolean exists(String pageName);
    
    public void create(Page page);
    
    public void update(Page page);
}
