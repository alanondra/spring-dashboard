package com.aondra.dashboard.services.mail.factories;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;

public class TextMailPartFactory
{
	public MimeBodyPart create(String textContent) throws MessagingException
	{
		MimeBodyPart part = new MimeBodyPart();

		part.setText(textContent, "utf-8");

		return part;
	}
}
