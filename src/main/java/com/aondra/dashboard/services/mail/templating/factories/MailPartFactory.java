package com.aondra.dashboard.services.mail.templating.factories;

import java.io.IOException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;

import com.aondra.dashboard.services.mail.entities.Attachment;
import com.aondra.dashboard.services.mail.entities.MailView;
import com.aondra.dashboard.services.mail.templating.MailTemplateEngine;
import com.aondra.dashboard.services.mail.templating.exceptions.TemplateMissingException;
import com.aondra.dashboard.services.mail.templating.exceptions.TemplateRenderException;

public class MailPartFactory
{
	protected final MailTemplateEngine templateEngine;

	public MailPartFactory(MailTemplateEngine templateEngine)
	{
		this.templateEngine = templateEngine;
	}

	public MimeBodyPart create(String content) throws MessagingException
	{
		return this.create(content, "text/plain; charset=utf-8");
	}

	public MimeBodyPart create(String content, String mimeType) throws MessagingException
	{
		MimeBodyPart part = new MimeBodyPart();
		part.setContent(content, mimeType);
		return part;
	}

	public MimeBodyPart create(MailView view) throws MessagingException, TemplateRenderException, TemplateMissingException
	{
		String html = this.templateEngine.render(view);
		return this.create(html, "text/html; charset=utf-8");
	}

	public MimeBodyPart create(MailView view, String mimeType) throws MessagingException, TemplateRenderException, TemplateMissingException
	{
		String html = this.templateEngine.render(view);
		return this.create(html, mimeType);
	}

	public MimeBodyPart create(Attachment attachment) throws MessagingException, IOException
	{
		MimeBodyPart attachmentPart = new MimeBodyPart();

		attachmentPart.setContent(
			attachment.getDataHandler().getContent(),
			attachment.getDataHandler().getContentType()
		);

		attachmentPart.setFileName(attachment.getFilename());

		return attachmentPart;
	}
}
