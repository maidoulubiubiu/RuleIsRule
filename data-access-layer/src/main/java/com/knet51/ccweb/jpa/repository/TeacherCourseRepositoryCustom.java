package com.knet51.ccweb.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.knet51.ccweb.jpa.entities.Teacher;
import com.knet51.ccweb.jpa.entities.teacher.TeacherCourse;

public interface TeacherCourseRepositoryCustom {
	
	TeacherCourse updateTeacherCourseDetail(TeacherCourse teacherCourse);
	List<TeacherCourse> getAllCourseById(Long teacher_id);
	
	//List<CourseBeans> listAllCourseBeans();
	List<String> getSchool();
	List<Teacher> showCourseTeacher(String schoolName);
	List<String> getCourseType();
	
	List<String> getCourseTypeByTeacherId(Long teacher_id);
}
