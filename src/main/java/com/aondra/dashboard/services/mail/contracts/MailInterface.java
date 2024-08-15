package com.aondra.dashboard.services.mail.contracts;

import java.util.Optional;
import java.io.UnsupportedEncodingException;
import java.util.List;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;

import com.aondra.dashboard.services.mail.MailPartFactorySet;

public interface MailInterface
{
	InternetAddress getRecipient() throws UnsupportedEncodingException;

	Optional<InternetAddress> getSender();

	List<InternetAddress> getCcs();

	List<InternetAddress> getBccs();

	Optional<String> getSubject();

	List<MimeBodyPart> getParts(MailPartFactorySet partFactories) throws MessagingException;
}
