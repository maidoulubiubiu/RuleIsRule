package com.knet51.ccweb.jpa.repository;

import java.util.List;

import com.knet51.ccweb.jpa.entities.WorkExp;

public interface WorkExpRepositoryCustom {
	
	List<WorkExp> findWorkExpList(Long teacher_id);
}
