package com.graphene.web.jpa.repository.tech;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.graphene.web.jpa.entity.tech.Technology;
import com.graphene.web.jpa.entity.user.User;


public interface TechRepository extends JpaRepository<Technology, Long>, JpaSpecificationExecutor<Technology>{
	Page<Technology> findAll(Pageable pageable);
	
	Page<Technology> findAllByUser(User user, Pageable pageable);
	Page<Technology> findAllByUserAndStatus(User user,Integer status, Pageable pageable);
	List<Technology> findAllListByUser(User user);
	
	Page<Technology> findAllByStatus(Integer status, Pageable pageable );
	List<Technology> findAllListByStatus(Integer status);

	
	Page<Technology> findAllByFocusAndStatus(Integer focus,Integer status, Pageable pageable );
	List<Technology> findAllListByFocusAndStatus(Integer focus,Integer status, Sort sort);
	
	Page<Technology> findAllByFocus(Integer focus, Pageable pageable );
}
