package com.aondra.dashboard.services.mail.contracts;

import java.util.Optional;

import com.aondra.dashboard.services.mail.templating.factories.MailPartFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;

public interface MailInterface
{
	InternetAddress getRecipient() throws UnsupportedEncodingException;

	Optional<InternetAddress> getSender();

	List<InternetAddress> getCcs();

	List<InternetAddress> getBccs();

	Optional<String> getSubject();

	List<MimeBodyPart> getParts(MailPartFactory mailPartFactory) throws IOException, MessagingException;
}
