package com.knet51.ccweb.jpa.entities.courses;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.google.gson.annotations.Expose;
import com.knet51.ccweb.jpa.entities.AbstractEntity;
import com.knet51.ccweb.jpa.entities.User;
import com.knet51.ccweb.jpa.entities.resource.ResourceType;

@Entity
public class CourseResource extends AbstractEntity {
	@Expose
	private String fileName;
	private String saveName;
	private String savePath;
	@Lob
	private String resourceDesc;
	private int lessonNum; //lesson 0ne,lesson two and so on....
	private String date;
	private Long course_id;
	private Long courseLessonId;
	private Integer status;
	private String relativePath;
	
	private String forbidden;
	
	@Expose
	@ManyToOne
	private ResourceType resourceType;
	@ManyToOne
	private User user;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	
	public Long getCourse_id() {
		return course_id;
	}
	public void setCourse_id(Long course_id) {
		this.course_id = course_id;
	}
	
	public String getSaveName() {
		return saveName;
	}
	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}
	
	public String getResourceDesc() {
		return resourceDesc;
	}

	public Long getCourseLessonId() {
		return courseLessonId;
	}
	public void setCourseLessonId(Long courseLessonId) {
		this.courseLessonId = courseLessonId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public ResourceType getResourceType() {
		return resourceType;
	}
	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}
	public int getLessonNum() {
		return lessonNum;
	}
	public void setLessonNum(int lessonNum) {
		this.lessonNum = lessonNum;
	}
	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getRelativePath() {
		return relativePath;
	}
	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}
	public CourseResource() {
		super();
	}
	public String getForbidden() {
		return forbidden;
	}
	public void setForbidden(String forbidden) {
		this.forbidden = forbidden;
	}
	
}
