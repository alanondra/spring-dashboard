package com.aondra.dashboard.services.mail.templating.exceptions;

public class TemplateRenderException extends RuntimeException
{
	public TemplateRenderException()
	{
		super();
	}

	public TemplateRenderException(String message)
	{
		super(message);
	}

	public TemplateRenderException(Throwable cause)
	{
		super(cause);
	}

	public TemplateRenderException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
