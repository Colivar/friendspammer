package nl.hu.sie.bep.friendspammer;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
	
	private EmailSender() {
	   throw new IllegalStateException("Utility class");
	}
	
	public static void sendEmail(String subject, String to, String messageBody, boolean asHtml) throws MessagingException {

		ServiceProvider provider = new ServiceProvider();
		Session session = provider.getSession();
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("spammer@spammer.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(to));
			message.setSubject(subject);
			
			if (asHtml) {
					message.setContent(messageBody, "text/html; charset=utf-8");
			} else {
				message.setText(messageBody);	
			}
			Transport.send(message);

			MongoSaver.saveEmail(to, "spammer@spamer.com", subject, messageBody, asHtml);

		} catch (MessagingException e) {
			throw new MessagingException();
		}
	}

	public static void sendEmail(String subject, String[] toList, String messageBody, boolean asHtml) throws MessagingException {
		ServiceProvider provider = new ServiceProvider();
		Session session = provider.getSession();

		try {

			for (int index = 0; index < toList.length; index++) {
			
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("spammer@spammer.com"));
				message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(toList[index]));
				message.setSubject(subject);
				
				if (asHtml) {
						message.setContent(messageBody, "text/html; charset=utf-8");
				} else {
					message.setText(messageBody);	
				}
				Transport.send(message);
	
				System.out.println("Done");
			}

		} catch (MessagingException e) {
			throw new MessagingException();
		}
	}
	

	
}
