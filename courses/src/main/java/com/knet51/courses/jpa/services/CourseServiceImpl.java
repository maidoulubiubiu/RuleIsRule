package com.knet51.courses.jpa.services;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.knet51.ccweb.jpa.entities.teacher.CourseResource;
import com.knet51.ccweb.jpa.repository.TeacherCourseResourceRepository;
@Transactional
@Service("courseService")
public class CourseServiceImpl implements CourseService {

	@Autowired
	private TeacherCourseResourceRepository teacherCourseResourceRepository;

	@Override
	public List<CourseResource> getResourceByCourseId(Long course_id) {
		List<CourseResource> list= new ArrayList<CourseResource>(); 
		try {
			list=teacherCourseResourceRepository.getResourceByCourseId(course_id);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return list;
	}

	@Override
	public List<CourseResource> getResourceByResourceOrder(String resourceOrder) {
		List<CourseResource> list= new ArrayList<CourseResource>(); 
		try {
			list=teacherCourseResourceRepository.getResourceByResourceOrder(resourceOrder);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return list;
	}
	
}
