package com.aondra.dashboard.mail;

import java.util.Optional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.aondra.dashboard.services.mail.MailPartFactorySet;
import com.aondra.dashboard.services.mail.contracts.MailInterface;
import com.aondra.dashboard.services.mail.factories.AttachmentMailPartFactory;
import com.aondra.dashboard.services.mail.factories.HtmlMailPartFactory;
import com.aondra.dashboard.services.mail.factories.TextMailPartFactory;

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
	public List<MimeBodyPart> getParts(MailPartFactorySet partFactories) throws IOException, MessagingException
	{
		List<MimeBodyPart> parts = new ArrayList<>();

		Optional<MimeBodyPart> text = this.getText(partFactories.text());
		Optional<MimeBodyPart> html = this.getHtml(partFactories.html());

		List<MimeBodyPart> attachments = this.getAttachments(partFactories.attachment());

		text.ifPresent(parts::add);
		html.ifPresent(parts::add);

		attachments.forEach((MimeBodyPart attachment) -> { parts.add(attachment); });

		return parts;
	}

	protected Optional<MimeBodyPart> getText(TextMailPartFactory factory) throws MessagingException
	{
		return Optional.empty();
	}

	protected Optional<MimeBodyPart> getHtml(HtmlMailPartFactory factory) throws IOException, MessagingException
	{
		return Optional.empty();
	}

	protected List<MimeBodyPart> getAttachments(AttachmentMailPartFactory factory)
	{
		return List.of();
	}
}
