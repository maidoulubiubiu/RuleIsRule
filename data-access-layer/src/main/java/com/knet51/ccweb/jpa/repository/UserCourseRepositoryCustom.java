package com.knet51.ccweb.jpa.repository;

import com.knet51.ccweb.jpa.entities.teacher.UserCourse;

public interface UserCourseRepositoryCustom {
	Double getMark(Long teacherCourseId);
}
