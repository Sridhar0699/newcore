package com.portal.internal.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CaptchaValidation {

	private String captchaString;
	private byte[] image;
	
	@JsonProperty(value="isCaptchaEnabled")     
	private boolean isCaptchaEnabled;
	
	public String getCaptchaString() {
		return captchaString;
	}
	public void setCaptchaString(String captchaString) {
		this.captchaString = captchaString;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public boolean isCaptchaEnabled() {
		return isCaptchaEnabled;
	}
	public void setIsCaptchaEnabled(boolean isCaptchaEnabled) {
		this.isCaptchaEnabled = isCaptchaEnabled;
	}
	
	
	
	
	
}
