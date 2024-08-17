package com.aondra.dashboard.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.pebbletemplates.pebble.PebbleEngine;

import com.aondra.dashboard.services.mail.templating.MailTemplateEngine;
import com.aondra.dashboard.services.mail.templating.contracts.TemplateResolverInterface;
import com.aondra.dashboard.services.mail.templating.resolvers.PebbleTemplateResolver;

@Configuration
public class MailConfig
{
	@Bean
	public MailTemplateEngine mailTemplateEngine(List<TemplateResolverInterface> resolvers)
	{
		return new MailTemplateEngine(resolvers);
	}

	@Bean
	public PebbleTemplateResolver pebbleTemplateResolver(PebbleEngine pebbleEngine)
	{
		return new PebbleTemplateResolver(pebbleEngine);
	}
}
