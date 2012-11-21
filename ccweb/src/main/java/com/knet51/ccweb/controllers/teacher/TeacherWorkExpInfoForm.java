package com.knet51.ccweb.controllers.teacher;

import org.hibernate.validator.constraints.NotEmpty;


public class TeacherWorkExpInfoForm {
	@NotEmpty
	private String company;
	@NotEmpty
	private String department;
	@NotEmpty
	private String position;
	@NotEmpty
	private String startTime;
	@NotEmpty
	private String endTime;
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public TeacherWorkExpInfoForm() {
		super();
	}
	
	

}