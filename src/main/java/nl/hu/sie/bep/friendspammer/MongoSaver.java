package nl.hu.sie.bep.friendspammer;

import java.util.Iterator;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoSaver {
	
	public static boolean saveEmail(String to, String from, String subject, String text, Boolean html) {
	    Logger logger = LoggerFactory.getLogger(MongoSaver.class);
		
		boolean success = true;
		
		try {
			ServiceProvider provider = new ServiceProvider();
			MongoDatabase db = provider.getDatabaseConnection();
			
			MongoCollection<Document> c = db.getCollection("email");
			
			Document  doc = new Document ("to", to)
			        .append("from", from)
			        .append("subject", subject)
			        .append("text", text)
			        .append("asHtml", html);
			c.insertOne(doc);
		} catch (MongoException mongoException) {
			logger.info("XXXXXXXXXXXXXXXXXX ERROR WHILE SAVING TO MONGO XXXXXXXXXXXXXXXXXXXXXXXXXX");
			mongoException.printStackTrace();
			success = false;
		}
		
		return success;
 		
	}
	
	
	public Iterator<Document> getAllMessages() {
		ServiceProvider provider = new ServiceProvider();
		
		MongoDatabase db = provider.getDatabaseConnection();
		
		MongoCollection<Document> c = db.getCollection("email");
		
		Iterator<Document> it = c.find().iterator();
	
		provider.closeClient();
		
		return it;
	}

}
