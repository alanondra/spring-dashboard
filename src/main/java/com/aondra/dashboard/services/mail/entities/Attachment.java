package com.aondra.dashboard.services.mail.entities;

import jakarta.activation.DataHandler;

public class Attachment
{
	protected final String filename;

	protected final DataHandler dataHandler;

	public Attachment(String filename, DataHandler dataHandler)
	{
		this.filename = filename;
		this.dataHandler = dataHandler;
	}

	public String getFilename()
	{
		return filename;
	}

	public DataHandler getDataHandler()
	{
		return dataHandler;
	}
}
