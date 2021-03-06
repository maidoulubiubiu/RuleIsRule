package com.graphene.web.jpa.repository.patent;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.graphene.web.jpa.entity.patent.Patent;
import com.graphene.web.jpa.entity.patent.PatentType;
import com.graphene.web.jpa.entity.user.User;



public interface PatentRepository extends JpaRepository<Patent, Long>,JpaSpecificationExecutor<Patent> {
	Page<Patent> findPatentByUser(User user, Pageable pageable);
	List<Patent> findPatentByUser(User user);
	
	Page<Patent> findPatentByUserAndStatus(User user,Integer status, Pageable pageable);
	List<Patent> findPatentByUserAndStatus(User user,Integer status);
	
	Page<Patent> findPatentByPatentField(String patentField, Pageable pageable);
	List<Patent> findPatentByPatentField(String patentField);
	
	Page<Patent> findAll(Pageable pageable);
	
	Page<Patent> findPatentByPatentNumLikeAndPatentNameLikeAndInventerLike(String patentNum,String patentName,String inventer,Pageable pageable);
	
	Page<Patent> findPatentByPatentTypeAndPatentNumLike(PatentType patentType,String patentNum, Pageable pageable);
	List<Patent> findPatentByPatentTypeAndPatentNumLike(PatentType patentType,String patentNum);
	
	Page<Patent> findPatentByPatentTypeAndPatentNameLike(PatentType patentType,String patentName, Pageable pageable);
	List<Patent> findPatentByPatentTypeAndPatentNameLike(PatentType patentType,String patentName);
	
	Page<Patent> findPatentByPatentTypeAndInventerLike(PatentType patentType,String inventer, Pageable pageable);
	List<Patent> findPatentByPatentTypeAndInventerLike(PatentType patentType,String inventer);
	
	Page<Patent> findPatentByPatentTypeAndPatentNumLikeAndPatentNameLikeAndPatentFieldLikeAndMainClassNumLikeAndClassNumLikeAndApplicantLikeAndInventerLikeAndPublishNumLike(PatentType patentType, String patentNum, String patentName,
			String patentField, String mainClassNum, String classNum,String applicant, String inventer, String publishNum, Pageable pageable);
	List<Patent> findPatentByPatentTypeAndPatentNumLikeAndPatentNameLikeAndPatentFieldLikeAndMainClassNumLikeAndClassNumLikeAndApplicantLikeAndInventerLikeAndPublishNumLike(PatentType patentType, String patentNum, String patentName,
			String patentField, String mainClassNum, String classNum,String applicant, String inventer, String publishNum);
	
	Page<Patent> findPatentByStatus(Integer status, Pageable pageable);
	
	Page<Patent> findPatentByCountryAndStatus(Integer country,Integer status, Pageable pageable);
	List<Patent> findPatentByCountry(Integer country);
	
	List<Patent> findPatentByCountryAndFocus(Integer country,Integer Focus);
	
	Page<Patent> findPatentByFocus(Integer focus,Pageable pageable);
	Page<Patent> findPatentByPatentNumLike(String patentNum,Pageable pageable);
	Page<Patent> findPatentByPatentNameLike(String patentName,Pageable pageable);
	

}
