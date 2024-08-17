package com.aondra.dashboard.services.mail.templating.exceptions;

public class TemplateMissingException extends RuntimeException
{
	public TemplateMissingException()
	{
		super();
	}

	public TemplateMissingException(String message)
	{
		super(message);
	}

	public TemplateMissingException(Throwable cause)
	{
		super(cause);
	}

	public TemplateMissingException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
