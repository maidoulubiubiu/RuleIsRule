package com.knet51.ccweb.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@Entity
@JsonIgnoreProperties(value={"course"})
public class Teacher {

	@Id
	private Long id;

	// @OneToOne(cascade = CascadeType.ALL)
	@OneToOne
	@PrimaryKeyJoinColumn(name = "id")
	private User user;


	private String role;
	private String title;
	private String college;
	private String school;
	private String teaching_subject;
	private String major;
	private String isEnterprise;
	private String forbidden;
	private String infor;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getForbidden() {
		return forbidden;
	}

	public void setForbidden(String forbidden) {
		this.forbidden = forbidden;
	}

	protected Teacher() {

	}

	public Teacher(User user) {
		this.user = user;
		setId(user.getId());
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getTeaching_subject() {
		return teaching_subject;
	}

	public void setTeaching_subject(String teaching_subject) {
		this.teaching_subject = teaching_subject;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	
	public String getIsEnterprise() {
		return isEnterprise;
	}

	public void setIsEnterprise(String isEnterprise) {
		this.isEnterprise = isEnterprise;
	}

	public String getInfor() {
		return infor;
	}

	public void setInfor(String infor) {
		this.infor = infor;
	}

	@Override
	public String toString() {
		return "Teacher [id=" + id + ", user=" + user + ", role=" + role
				+ ",title=" + title + " college=" + college + ", school="
				+ school + ", teaching_subject=" + teaching_subject
				+ ", major=" + major + "]";
	}

}