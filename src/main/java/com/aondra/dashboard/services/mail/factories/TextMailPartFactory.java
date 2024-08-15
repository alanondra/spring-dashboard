package com.aondra.dashboard.services.mail.factories;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;

public class TextMailPartFactory
{
	public MimeBodyPart create(String htmlContent) throws MessagingException
	{
		MimeBodyPart part = new MimeBodyPart();

		part.setText(htmlContent, "utf-8");

		return part;
	}
}
