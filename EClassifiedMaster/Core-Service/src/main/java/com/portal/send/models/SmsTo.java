package com.portal.send.models;

import java.util.List;

public class SmsTo {

	private String from;

	private String to;

	private String templateName;

	private List<String> vars;

	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @param from
	 *            the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @param to
	 *            the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * @return the templateName
	 */
	public String getTemplateName() {
		return templateName;
	}

	/**
	 * @param templateName
	 *            the templateName to set
	 */
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	/**
	 * @return the vars
	 */
	public List<String> getVars() {
		return vars;
	}

	/**
	 * @param vars
	 *            the vars to set
	 */
	public void setVars(List<String> vars) {
		this.vars = vars;
	}
}
