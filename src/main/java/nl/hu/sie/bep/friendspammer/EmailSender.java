package nl.hu.sie.bep.friendspammer;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailSender {
	private static Logger logger = LoggerFactory.getLogger(MongoSaver.class);
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
			logger.info(e.getMessage());
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
	
				logger.info("Done");
			}

		} catch (MessagingException e) {
			logger.info(e.getMessage());
		}
	}
	

	
}
