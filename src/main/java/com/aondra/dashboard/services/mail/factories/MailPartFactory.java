package com.aondra.dashboard.services.mail.factories;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;

public class MailPartFactory
{
	public MimeBodyPart create(Object content, String mimeType) throws MessagingException {
		MimeBodyPart part = new MimeBodyPart();
		part.setContent(content, mimeType);
		return part;
	}
}
