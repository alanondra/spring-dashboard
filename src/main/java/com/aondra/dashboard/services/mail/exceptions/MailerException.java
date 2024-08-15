package com.aondra.dashboard.services.mail.exceptions;

public class MailerException extends RuntimeException
{
	public MailerException()
	{
		super();
	}

	public MailerException(String message)
	{
		super(message);
	}

	public MailerException(Throwable cause)
	{
		super(cause);
	}

	public MailerException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
