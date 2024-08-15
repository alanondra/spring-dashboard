package com.aondra.dashboard.services.mail;

import com.aondra.dashboard.services.mail.factories.AttachmentMailPartFactory;
import com.aondra.dashboard.services.mail.factories.HtmlMailPartFactory;
import com.aondra.dashboard.services.mail.factories.MailPartFactory;
import com.aondra.dashboard.services.mail.factories.TextMailPartFactory;

public class MailPartFactorySet
{
	protected AttachmentMailPartFactory attachmentFactory;
	protected HtmlMailPartFactory htmlFactory;
	protected MailPartFactory partFactory;
	protected TextMailPartFactory textFactory;

	public MailPartFactorySet(
		AttachmentMailPartFactory attachmentFactory,
		HtmlMailPartFactory htmlFactory,
		MailPartFactory partFactory,
		TextMailPartFactory textFactory
	) {
		this.attachmentFactory = attachmentFactory;
		this.htmlFactory = htmlFactory;
		this.partFactory = partFactory;
		this.textFactory = textFactory;
	}

	public AttachmentMailPartFactory attachment()
	{
		return attachmentFactory;
	}

	public HtmlMailPartFactory html() {
		return htmlFactory;
	}

	public MailPartFactory part()
	{
		return partFactory;
	}

	public TextMailPartFactory text()
	{
		return textFactory;
	}
}
