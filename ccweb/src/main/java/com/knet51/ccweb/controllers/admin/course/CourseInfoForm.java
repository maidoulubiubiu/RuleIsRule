package com.knet51.ccweb.controllers.admin.course;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class CourseInfoForm {
	@NotEmpty
	@Size(min=1,max=100)
	private String courseName;
	@NotEmpty
	private String courseDesc;
	
	private Long courseType;
	
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseDesc() {
		return courseDesc;
	}
	public void setCourseDesc(String courseDesc) {
		this.courseDesc = courseDesc;
	}
	public Long getCourseType() {
		return courseType;
	}
	public void setCourseType(Long courseType) {
		this.courseType = courseType;
	}
	
	
}
