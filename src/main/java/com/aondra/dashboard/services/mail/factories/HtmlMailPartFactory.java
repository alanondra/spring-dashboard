package com.aondra.dashboard.services.mail.factories;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;

import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;

public class HtmlMailPartFactory
{
	protected PebbleEngine pebble;

	public HtmlMailPartFactory(PebbleEngine pebble)
	{
		this.pebble = pebble;
	}

	public MimeBodyPart create(String html) throws MessagingException
	{
		MimeBodyPart part = new MimeBodyPart();

		part.setContent(html, "text/html; charset=utf-8");

		return part;
	}

	public MimeBodyPart create(String templateName, Map<String, Object> data) throws IOException, MessagingException
	{
		String html = this.render(templateName, data);

		return this.create(html);
	}

	protected String render(String templateName, Map<String, Object> data) throws IOException
	{
		PebbleTemplate template = this.pebble.getTemplate(templateName);

		if (template == null) {
			throw new FileNotFoundException(String.format("Could not resolve the template '%s'.", templateName));
		}

		StringWriter writer = new StringWriter();

		template.evaluate(writer, data);

		return writer.toString();
	}
}
