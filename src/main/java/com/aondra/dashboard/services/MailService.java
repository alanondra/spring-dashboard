package com.aondra.dashboard.services;

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
import com.aondra.dashboard.services.mail.MailPartFactorySet;
import com.aondra.dashboard.services.mail.contracts.MailInterface;
import com.aondra.dashboard.services.mail.entities.MailerDaemon;
import com.aondra.dashboard.services.mail.exceptions.MailerException;

@Service
public class MailService
{
	protected JavaMailSender mailSender;
	protected MailPartFactorySet mailPartFactories;
	protected MailerDaemon mailerDaemon;

	public MailService(
		JavaMailSender mailSender,
		MailPartFactorySet mailPartFactories,
		MailerDaemon mailerDaemon
	) {
		this.mailSender = mailSender;
		this.mailPartFactories = mailPartFactories;
		this.mailerDaemon = mailerDaemon;
	}

	public JavaMailSender getMailSender()
	{
		return this.mailSender;
	}

	public MailPartFactorySet getMailPartFactories()
	{
		return this.mailPartFactories;
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
					throw new UserException("Invalid sender.", e);
				}
			});

			try {
				helper.setTo(mail.getRecipient());
			} catch (UnsupportedEncodingException e) {
				throw new UserException("Invalid recipient.", e);
			}

			mail.getCcs().forEach((InternetAddress cc) -> {
				try {
					helper.addCc(cc);
				} catch (MessagingException e) {
					throw new UserException("Invalid carbon copy recipient.", e);
				}
			});

			mail.getBccs().forEach((InternetAddress bcc) -> {
				try {
					helper.addBcc(bcc);
				} catch (MessagingException e) {
					throw new UserException("Invalid blind carbon copy recipient.", e);
				}
			});

			mail.getSubject().ifPresent((String subject) -> {
				try {
					helper.setSubject(subject);
				} catch (MessagingException e) {
					throw new UserException("Invalid subject.", e);
				}
			});

			// Add parts
			mail.getParts(this.mailPartFactories).forEach((MimeBodyPart part) -> {
				try {
					helper.getMimeMultipart().addBodyPart(part);
				} catch (MessagingException e) {
					throw new UserException("Invalid message.", e);
				}
			});

			return mimeMessage;
		} catch (MessagingException e) {
			throw new MailerException("Failed to create MimeMessage.");
		}
	}
}
