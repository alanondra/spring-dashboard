package com.aondra.dashboard.services.mail.templating.contracts;

import java.util.Optional;

import com.aondra.dashboard.services.mail.entities.MailView;
import com.aondra.dashboard.services.mail.templating.exceptions.TemplateRenderException;

public interface TemplateResolverInterface
{
	Optional<String> resolve(MailView view) throws TemplateRenderException;
}
