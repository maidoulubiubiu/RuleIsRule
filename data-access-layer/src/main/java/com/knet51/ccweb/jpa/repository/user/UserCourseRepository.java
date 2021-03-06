package com.knet51.ccweb.jpa.repository.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.knet51.ccweb.jpa.entities.courses.UserCourse;

@Transactional
@SuppressWarnings("unchecked")
public interface UserCourseRepository extends JpaRepository<UserCourse, Long>, JpaSpecificationExecutor<UserCourse>, UserCourseRepositoryCustom{
	
	Page<UserCourse> findUserCourseByTeachercourseid(Long teachercourseid ,Pageable pageable);
	
	@Query("SELECT u FROM UserCourse u where u.teachercourseid =:teachercourseid AND u.teachercourseid IN (SELECT c.teachercourseid FROM UserCourse c)")
	List<UserCourse> findByTeachercourseid(@Param("teachercourseid") Long teacherCourseId);
	
	UserCourse save(UserCourse userCourse);
	
	List<UserCourse> findUserCourseByUserid(Long user_id);
	
	@Query("select c from UserCourse c where c.teachercourseid = :teachercourseid and c.userid = :userid")
	UserCourse findByTeachercourseidAndUserid(@Param("teachercourseid") Long teachercourseid, @Param("userid") Long userid);
	
	Page<UserCourse> findByUserid(Long user_id ,Pageable pageable);
	
	
}
