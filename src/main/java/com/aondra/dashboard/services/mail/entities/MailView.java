package com.aondra.dashboard.services.mail.entities;

import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

public class MailView
{
	protected String templateName;

	protected Map<String, Object> data;

	public MailView(String templateName)
	{
		this.templateName = templateName;
		this.data = new HashMap<>();
	}

	public MailView(String templateName, Map<String, Object> data)
	{
		this.templateName = templateName;
		this.data = data;
	}

	public String getTemplateName()
	{
		return this.templateName;
	}

	public void setTemplateName(String value)
	{
		this.templateName = value;
	}

	public Map<String,Object> getData()
	{
		return this.data;
	}

	public void setData(Map<String,Object> value)
	{
		this.data = value;
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == this) {
			return true;
		}

		if (!(o instanceof MailView)) {
			return false;
		}

		MailView other = (MailView) o;

		return (
			Objects.equals(templateName, other.templateName)
			&& Objects.equals(data, other.data)
		);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(templateName, data);
	}

	@Override
	public String toString()
	{
		return String.format(
			"{templateName='%s',data=%s}",
			this.getTemplateName(),
			this.getData().toString()
		);
	}
}
