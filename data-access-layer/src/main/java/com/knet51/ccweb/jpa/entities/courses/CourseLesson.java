package com.knet51.ccweb.jpa.entities.courses;

import javax.persistence.Entity;

import com.knet51.ccweb.jpa.entities.AbstractEntity;
@Entity
public class CourseLesson extends AbstractEntity{
	private Long courseId;
	private int lessonNum;
	private String status; //check the lessonNum is the max number or not
	public Long getCourseId() {
		return courseId;
	}
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	public int getLessonNum() {
		return lessonNum;
	}
	public void setLessonNum(int lessonNum) {
		this.lessonNum = lessonNum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
