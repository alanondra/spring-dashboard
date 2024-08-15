package com.aondra.dashboard.services.mail.entities;

import java.util.Objects;
import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Value;

import jakarta.mail.internet.InternetAddress;

public final class MailerDaemon
{
	private String name;

	private String email;

	public MailerDaemon(
		@Value("${mail.mailer.name}") String name,
		@Value("${mail.mailer.email}") String email
	) {
		this.name = name;
		this.email = email;
	}

	public String getName()
	{
		return this.name;
	}

	public String getEmail()
	{
		return this.email;
	}

	public InternetAddress getInternetAddress() throws UnsupportedEncodingException
	{
		return new InternetAddress(this.getEmail(), this.getName());
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == this) {
			return true;
		}

		if (!(o instanceof MailerDaemon)) {
			return false;
		}

		MailerDaemon other = (MailerDaemon) o;

		return (
			Objects.equals(this.name, other.name)
			&& Objects.equals(this.email, other.email)
		);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(name, email);
	}

	@Override
	public String toString()
	{
		return String.format("\"%s\" <%s>", this.getName(), this.getEmail());
	}
}
