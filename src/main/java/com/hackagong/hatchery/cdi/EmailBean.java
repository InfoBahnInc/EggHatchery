package com.hackagong.hatchery.cdi;

import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.metawidget.util.LogUtils;
import org.metawidget.util.LogUtils.Log;

/**
 * Application-wide configuration settings
 */

@ApplicationScoped
public class EmailBean {

	//
	// Private statics
	//

	private final static Log LOG = LogUtils.getLog(EmailBean.class);

	//
	// Public methods
	//

	public void sendNotification(String subject, String messageText) {

		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", "mail.kennardconsulting.com");

		Session session = Session.getDefaultInstance(properties);

		try {
			MimeMessage mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress(
					"hatchery@kennardconsulting.com", "Hackagong Hatchery"));
			mimeMessage.addRecipient(Message.RecipientType.TO,
					new InternetAddress("megabyte@me.com"));
			mimeMessage.addRecipient(Message.RecipientType.TO,
					new InternetAddress("arbixal@hotmail.com"));
			mimeMessage.setSubject(subject);
			mimeMessage.setText(messageText);
			// TODO: Transport.send(mimeMessage);
			
			LOG.info("Sent {0}: {1}", subject,messageText);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
