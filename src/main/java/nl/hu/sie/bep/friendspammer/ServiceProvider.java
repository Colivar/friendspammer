package nl.hu.sie.bep.friendspammer;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

public class ServiceProvider {
	private static final String userName = "YOUR USER";
	private static final String password = "YOUR PASS";
	private static final String database = "friendspammer";
	private MongoClient mongoClient;
	private MongoCredential credential;
	
	public ServiceProvider() {
		MongoCredential credential = MongoCredential.createCredential(userName, database, password.toCharArray());
		mongoClient = new MongoClient(new ServerAddress("YOUR HOST", 27939), credential, MongoClientOptions.builder().build());
	}
	
	public MongoDatabase getDatabaseConnection() {
		MongoDatabase db = mongoClient.getDatabase( database );
		
		return db;
	}
	
	public void closeClient() {
		mongoClient.close();
	}
}
