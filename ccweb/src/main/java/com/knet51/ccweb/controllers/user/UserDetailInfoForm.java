package com.knet51.ccweb.controllers.user;

import org.hibernate.validator.constraints.NotEmpty;

public class UserDetailInfoForm {

	@NotEmpty
	private String name;
	@NotEmpty
	private String cellPhone;
	@NotEmpty
	private String gender;

	private String promote;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPromote() {
		return promote;
	}

	public void setPromote(String promote) {
		this.promote = promote;
	}

}
