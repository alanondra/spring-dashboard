package com.aondra.dashboard.mail;

import java.util.Optional;
import java.util.Collections;
import java.util.List;

import com.aondra.dashboard.services.mail.contracts.MailInterface;

import jakarta.mail.internet.InternetAddress;

public abstract class AbstractMail implements MailInterface
{
	@Override
	public Optional<InternetAddress> getSender() {
		return Optional.empty();
	}

	@Override
	public List<InternetAddress> getCcs()
	{
		return Collections.emptyList();
	}

	@Override
	public List<InternetAddress> getBccs()
	{
		return Collections.emptyList();
	}

	@Override
	public Optional<String> getSubject() {
		return Optional.empty();
	}
}
