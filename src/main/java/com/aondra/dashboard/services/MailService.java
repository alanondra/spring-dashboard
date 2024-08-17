package com.aondra.dashboard.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Service;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;

import com.aondra.dashboard.exceptions.UserException;
import com.aondra.dashboard.services.mail.contracts.MailInterface;
import com.aondra.dashboard.services.mail.entities.MailerDaemon;
import com.aondra.dashboard.services.mail.exceptions.MailerException;
import com.aondra.dashboard.services.mail.templating.factories.MailPartFactory;

@Service
public class MailService
{
	protected JavaMailSender mailSender;
	protected MailPartFactory mailPartFactory;
	protected MailerDaemon mailerDaemon;

	public MailService(
		JavaMailSender mailSender,
		MailPartFactory mailPartFactory,
		MailerDaemon mailerDaemon
	) {
		this.mailSender = mailSender;
		this.mailPartFactory = mailPartFactory;
		this.mailerDaemon = mailerDaemon;
	}

	public JavaMailSender getMailSender()
	{
		return this.mailSender;
	}

	public MailPartFactory getmailPartFactory()
	{
		return this.mailPartFactory;
	}

	public MailerDaemon getMailerDaemon()
	{
		return this.mailerDaemon;
	}

	public void send(MailInterface mail) throws MailerException, UserException
	{
		try {
			MimeMessage message = this.getMessage(mail);
			this.mailSender.send(message);
		} catch (MailAuthenticationException e) {
			throw new MailerException("Failed authentication.", e);
		} catch (MailSendException e) {
			throw new MailerException("Failed to send mail.", e);
		} catch (MailException e) {
			throw new MailerException(e);
		}
	}

	protected MimeMessage getMessage(MailInterface mail) throws MailerException, UserException
	{
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

			try {
				helper.setFrom(mailerDaemon.getInternetAddress());
			} catch (UnsupportedEncodingException e) {
				throw new MailerException("Failed setting the MailerDaemon.", e);
			}

			mail.getSender().ifPresent((InternetAddress sender) -> {
				try {
					helper.setReplyTo(sender);
				} catch (MessagingException e) {
					throw new UserException(
						String.format("Invalid sender %s.", sender.toString()),
						e
					);
				}
			});

			try {
				helper.setTo(mail.getRecipient());
			} catch (UnsupportedEncodingException e) {
				throw new UserException(
					String.format("Invalid recipient %s.", mail.getRecipient().toString()),
					e
				);
			}

			mail.getCcs().forEach((InternetAddress cc) -> {
				try {
					helper.addCc(cc);
				} catch (MessagingException e) {
					throw new UserException(
						String.format("Invalid carbon copy recipient %s.", cc.toString()),
						e
					);
				}
			});

			mail.getBccs().forEach((InternetAddress bcc) -> {
				try {
					helper.addBcc(bcc);
				} catch (MessagingException e) {
					throw new UserException(
						String.format("Invalid blind carbon copy recipient %s.", bcc.toString()),
						e
					);
				}
			});

			mail.getSubject().ifPresent((String subject) -> {
				try {
					helper.setSubject(subject);
				} catch (MessagingException e) {
					throw new UserException(
						String.format("Invalid subject.", subject),
						e
					);
				}
			});

			// Add parts
			mail.getParts(this.mailPartFactory).forEach((MimeBodyPart part) -> {
				try {
					helper.getMimeMultipart().addBodyPart(part);
				} catch (MessagingException e) {
					throw new UserException("Invalid message.", e);
				}
			});

			return mimeMessage;
		} catch (IOException e) {
			throw new MailerException("Failed to format MimeMessage or handle attachments.", e);
		} catch (MessagingException e) {
			throw new MailerException("Failed to create MimeMessage.", e);
		}
	}
}
