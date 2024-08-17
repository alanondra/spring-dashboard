package com.aondra.dashboard.services.mail.templating.resolvers;

import java.util.Optional;

import java.io.IOException;
import java.io.StringWriter;

import com.aondra.dashboard.services.mail.entities.MailView;
import com.aondra.dashboard.services.mail.templating.contracts.TemplateResolverInterface;
import com.aondra.dashboard.services.mail.templating.exceptions.TemplateRenderException;

import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.error.PebbleException;
import io.pebbletemplates.pebble.template.PebbleTemplate;

public class PebbleTemplateResolver implements TemplateResolverInterface
{
	protected PebbleEngine pebble;

	public PebbleTemplateResolver(PebbleEngine pebble)
	{
		this.pebble = pebble;
	}

	@Override
	public Optional<String> resolve(MailView view) throws TemplateRenderException
	{
		try {
			PebbleTemplate template = this.pebble.getTemplate(view.getTemplateName());

			if (template == null) {
				return Optional.empty();
			}

			StringWriter writer = new StringWriter();

			template.evaluate(writer, view.getData());

			return Optional.of(writer.toString());
		} catch (IOException e) {
			throw new TemplateRenderException("Failed to render Pebble template.", e);
		} catch (PebbleException e) {
			throw new TemplateRenderException("Failed to render Pebble template.", e);
		}
	}
}
