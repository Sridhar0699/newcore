package com.portal.user.to;

public class ApprovalUserTo {

	private Integer userId = null;

	private String logonId = null;

	private String firstName = null;
	
	private String middleName = null;

	private String lastName = null;
	
	private String email = null;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getLogonId() {
		return logonId;
	}

	public void setLogonId(String logonId) {
		this.logonId = logonId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
