package com.knet51.diplomat.jpa.services.projects;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.knet51.ccweb.jpa.entities.User;
import com.knet51.ccweb.jpa.entities.projects.Projects;

public interface ProjectsService {
	Projects findOne(Long id);
	Projects create(Projects projects);
	Projects update(Projects projects);
	void dele(Long id);
	
	Page<Projects> findProjectsPage(int pageNumber, int pageSize);
	Page<Projects> findProjectsByStatus(int pageNumber, int pageSize,Integer status);
	List<Projects> findProjectsListByStatus(Integer status);
	
	Page<Projects> findProjectsByUser(User user, int pageNumber, int pageSize);
	Page<Projects> findProjectsByUserAndStatus(User user,Integer status, int pageNumber, int pageSize);
	
	List<Projects> findProjectsListByUser(User user);
	
	Page<Projects> findOrderProjectsByUser(Long userid, int pageNumber,int pageSize);
	
}
