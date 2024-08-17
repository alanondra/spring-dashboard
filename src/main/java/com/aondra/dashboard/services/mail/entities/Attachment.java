package com.aondra.dashboard.services.mail.entities;

import jakarta.activation.DataHandler;
import java.util.Objects;

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

	@Override
	public boolean equals(Object o)
	{
		if (o == this) {
			return true;
		}

		if (!(o instanceof Attachment)) {
			return false;
		}

		Attachment other = (Attachment) o;

		return (
			Objects.equals(filename, other.filename)
			&& Objects.equals(dataHandler, other.dataHandler)
		);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(filename, dataHandler);
	}

	@Override
	public String toString()
	{
		return String.format(
			"{filename='%s',dataHandler=%s}",
			this.getFilename(),
			this.getDataHandler().toString()
		);
	}
}
