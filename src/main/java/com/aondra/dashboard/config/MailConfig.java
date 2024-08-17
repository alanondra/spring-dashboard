package com.aondra.dashboard.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.pebbletemplates.pebble.PebbleEngine;

import com.aondra.dashboard.services.mail.entities.MailerDaemon;
import com.aondra.dashboard.services.mail.entities.Webmaster;
import com.aondra.dashboard.services.mail.templating.MailTemplateEngine;
import com.aondra.dashboard.services.mail.templating.contracts.TemplateResolverInterface;
import com.aondra.dashboard.services.mail.templating.factories.MailPartFactory;
import com.aondra.dashboard.services.mail.templating.resolvers.PebbleTemplateResolver;

@Configuration
public class MailConfig
{
	@Value("${mail.mailer.name}")
	protected String mailerName;

	@Value("${mail.mailer.email}")
	protected String mailerEmail;

	@Value("${mail.webmaster.name}")
	protected String webmasterName;

	@Value("${mail.webmaster.email}")
	protected String webmasterEmail;

	@Bean
	public MailerDaemon mailerDaemon()
	{
		return new MailerDaemon(this.mailerName, this.mailerEmail);
	}

	@Bean
	public Webmaster webmaster()
	{
		return new Webmaster(this.webmasterName, this.webmasterEmail);
	}

	@Bean
	public MailPartFactory mailPartFactory(MailTemplateEngine templateEngine)
	{
		return new MailPartFactory(templateEngine);
	}

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
