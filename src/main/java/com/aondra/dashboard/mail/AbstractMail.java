package com.aondra.dashboard.mail;

import java.util.Optional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.aondra.dashboard.services.mail.contracts.MailInterface;
import com.aondra.dashboard.services.mail.templating.factories.MailPartFactory;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;

public abstract class AbstractMail implements MailInterface
{
	@Override
	public Optional<InternetAddress> getSender() {
		return Optional.empty();
	}

	@Override
	public List<InternetAddress> getCcs()
	{
		return Collections.emptyList();
	}

	@Override
	public List<InternetAddress> getBccs()
	{
		return Collections.emptyList();
	}

	@Override
	public Optional<String> getSubject() {
		return Optional.empty();
	}

	@Override
	public List<MimeBodyPart> getParts(MailPartFactory mailPartFactory) throws IOException, MessagingException
	{
		List<MimeBodyPart> parts = new ArrayList<>();

		Optional<MimeBodyPart> text = this.getText(mailPartFactory);
		Optional<MimeBodyPart> html = this.getHtml(mailPartFactory);

		List<MimeBodyPart> attachments = this.getAttachments(mailPartFactory);

		text.ifPresent(parts::add);
		html.ifPresent(parts::add);

		parts.addAll(attachments);

		return parts;
	}

	protected Optional<MimeBodyPart> getText(MailPartFactory mailPartFactory) throws MessagingException
	{
		return Optional.empty();
	}

	protected Optional<MimeBodyPart> getHtml(MailPartFactory mailPartFactory) throws IOException, MessagingException
	{
		return Optional.empty();
	}

	protected List<MimeBodyPart> getAttachments(MailPartFactory mailPartFactory)
	{
		return List.of();
	}
}
