package nl.hu.sie.bep.friendspammer;

import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

public class ServiceProvider {
	private static final String USERNAME = "YOUR USER";
	private static final String PASSWORD = "YOUR PASS";
	private static final String DATABASE = "friendspammer";
	private MongoClient mongoClient;
	
	public ServiceProvider() {
		MongoCredential credential = MongoCredential.createCredential(USERNAME, PASSWORD, DATABASE.toCharArray());
		mongoClient = new MongoClient(new ServerAddress("YOUR HOST", 27939), credential, MongoClientOptions.builder().build());
	}
	
	public MongoDatabase getDatabaseConnection() {
		return mongoClient.getDatabase( DATABASE );
	}
	
	public void closeClient() {
		mongoClient.close();
	}
	
	public Session getSession() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.mailtrap.io");
		props.put("mail.smtp.port", "2525");
		props.put("mail.smtp.auth", "true");
		
		final String username = "YOUR MAIL USERNAME";
		final String password = "YOUR MAIL PASSWORD";

		return Session.getInstance(props,
				  new javax.mail.Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });
	}
}
