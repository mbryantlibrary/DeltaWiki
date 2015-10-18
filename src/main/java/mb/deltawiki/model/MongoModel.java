package mb.deltawiki.model;

import static com.mongodb.client.model.Filters.eq;

import org.bson.BSON;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoModel implements Model {

	private static final Logger logger = LoggerFactory.getLogger(MongoModel.class);

	private MongoClient mongoClient;
	private MongoDatabase mongoDatabase;
	private MongoCollection<Document> mongoCollection;
	
	private static final String PAGE_SEARCH = "pageName";

	public MongoModel() {
	}
	
	public static MongoModel injectMongo(MongoClient clientToInject) {
		MongoModel model = new MongoModel();
		model.mongoClient = clientToInject;
		return model;
	}


	private void init() {
		mongoClient = new MongoClient();
	}

	public void connect(String dbName) {
		connect(dbName, "pages");
	}

	public void connect(String dbName, String collectionName) {
		if (mongoClient == null) {
			init();
		}
		// will create database if it doesn't exist
		mongoDatabase = mongoClient.getDatabase(dbName);
		mongoCollection = mongoDatabase.getCollection(collectionName);
		mongoCollection.createIndex(new Document(PAGE_SEARCH, 1));
	}

	@Override
	public Page getPage(String pageName) throws PageDoesntExistException {
		logger.debug("Retrieving page '{}'", pageName);
		Document doc = mongoCollection.find(eq(PAGE_SEARCH, pageName)).limit(1).first();
		if (doc == null) {
			throw new PageDoesntExistException();
		}

		return new Page(doc);
	}

	@Override
	public boolean exists(String pageName) {
		return mongoCollection.count(eq(PAGE_SEARCH, pageName)) > 0;
	}

	@Override
	public void create(Page page) {
		if (exists(page.pageName)) {
			throw new PageAlreadyExistsException();
		}
		mongoCollection.insertOne(page.asDocument());
	}

	@Override
	public void update(Page page) {
		mongoCollection.replaceOne(eq(PAGE_SEARCH, page.pageName), page.asDocument());
	}

	public void delete(String pageName) {
		mongoCollection.deleteOne(eq(PAGE_SEARCH, pageName));
	}

}
