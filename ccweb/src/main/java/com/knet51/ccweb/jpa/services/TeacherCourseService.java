package com.knet51.ccweb.jpa.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.knet51.ccweb.jpa.entities.Teacher;
import com.knet51.ccweb.jpa.entities.User;
import com.knet51.ccweb.jpa.entities.courses.CourseType;
import com.knet51.ccweb.jpa.entities.courses.TeacherCourse;

public interface TeacherCourseService {
	Page<TeacherCourse> findAllCourse(int pageNum, int pageSize);
	TeacherCourse findOneById(Long id);
	TeacherCourse createTeacherCourse(TeacherCourse teacherCourse);
	TeacherCourse updateTeacherCourse(TeacherCourse teacherCourse);
	List<TeacherCourse> getAllTeacherCourseByUseridAndPublish(Long teacher_id,Integer Publish);
	void deleTeacherCourse(Long course_id);
	Page<TeacherCourse> findAllCourseByUser(int pageNum, int pageSize, User user);
	//List<CourseBeans> getAllCourseBeans();
	List<String> getAllSchool();
	List<Teacher> getAllCourseTeacher(String schoolName);
	Page<TeacherCourse> findTeacherCourseByUserAndPublish(int pageNum, int pageSize, User user,Integer publish);
	Page<TeacherCourse> findTeacherCourseByUserAndPublishGreaterThan(int pageNum, int pageSize,User user,Integer publish);
	TeacherCourse getTeacherCourseByCourseName(String cousername,Long teacherId);
	
	Page<TeacherCourse> findTeacherCourseByUserAndPublishAndCType(int pageNum, int pageSize, User user,Integer publish,CourseType cType);
	
	/*  for super Admin */
	List<TeacherCourse> findCourseByUserAndPublishGreaterThanForSuperAdmin(User user,Integer publish);
	Page<TeacherCourse> findCourseByUserAndPublishGreaterThanForSuperAdmin(User user,Integer publish,int pageNum, int pageSize);
	Page<TeacherCourse> findCourseByPublishGreaterThanForSuperAdmin(Integer publish,int pageNum, int pageSize);
	List<TeacherCourse> findAllForAdmin();
}
