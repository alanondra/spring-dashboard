package com.aondra.dashboard.services.mail.factories;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;

public class HtmlMailPartFactory
{
	public MimeBodyPart create(String htmlContent) throws MessagingException
	{
		MimeBodyPart part = new MimeBodyPart();

		part.setContent(htmlContent, "text/html; charset=utf-8");

		return part;
	}
}
