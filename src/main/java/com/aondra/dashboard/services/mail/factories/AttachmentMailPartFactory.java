package com.aondra.dashboard.services.mail.factories;

import java.io.IOException;

import com.aondra.dashboard.services.mail.entities.Attachment;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;

public class AttachmentMailPartFactory
{
	public MimeBodyPart create(Attachment attachment) throws MessagingException, IOException
	{
		MimeBodyPart attachmentPart = new MimeBodyPart();

		attachmentPart.setContent(
			attachment.getDataHandler().getContent(),
			attachment.getDataHandler().getContentType()
		);

		attachmentPart.setFileName(attachment.getFilename());

		return attachmentPart;
	}
}
