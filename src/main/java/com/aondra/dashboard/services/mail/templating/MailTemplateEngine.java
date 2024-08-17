package com.aondra.dashboard.services.mail.templating;

import java.util.List;
import java.util.Optional;

import com.aondra.dashboard.services.mail.entities.MailView;
import com.aondra.dashboard.services.mail.templating.contracts.TemplateResolverInterface;
import com.aondra.dashboard.services.mail.templating.exceptions.TemplateMissingException;
import com.aondra.dashboard.services.mail.templating.exceptions.TemplateRenderException;

public class MailTemplateEngine
{
	protected final List<TemplateResolverInterface> resolvers;

	public MailTemplateEngine(List<TemplateResolverInterface> resolvers)
	{
		this.resolvers = resolvers;
	}

	public String render(MailView view) throws TemplateMissingException, TemplateRenderException
	{
		for (TemplateResolverInterface resolver : resolvers) {
			Optional<String> result = resolver.resolve(view);

			if (result.isPresent()) {
				return result.get();
			}
		}

		throw new TemplateMissingException(String.format("Template '%s' not found.", view.getTemplateName()));
	}
}
