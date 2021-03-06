package com.knet51.patents.jpa.services.applyright;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knet51.ccweb.jpa.entities.User;
import com.knet51.ccweb.jpa.entities.UserRight;
import com.knet51.ccweb.jpa.entities.applyright.ApplyRight;
import com.knet51.ccweb.jpa.entities.applyright.CoApplyRight;
import com.knet51.ccweb.jpa.repository.applyright.ApplyRightRepository;
import com.knet51.ccweb.jpa.repository.applyright.CoApplyRightRepository;
import com.knet51.ccweb.jpa.repository.user.UserRightRepository;
import com.knet51.patents.controllers.common.defs.GlobalDefs;
@Service("applyRightService")
public class ApplyRightServiceImpl implements ApplyRightService {
	@Autowired
	private ApplyRightRepository applyRightRepository;
	@Autowired
	private UserRightRepository userRightRepository;
	@Autowired
	private CoApplyRightRepository coApplyRightRepository;
	
	@Override
	public ApplyRight create(ApplyRight applyRight) {
		return applyRightRepository.save(applyRight);
	}

	@Override
	public ApplyRight update(ApplyRight applyRight) {
		return applyRightRepository.saveAndFlush(applyRight);
	}

	@Override
	public void delete(Long id) {
		applyRightRepository.delete(id);

	}

	@Override
	public ApplyRight find(Long id) {
		return applyRightRepository.findOne(id);
	}

	@Override
	public Page<ApplyRight> findApplyRightPage(int pageNumber, int pageSize) {
		Pageable pageable = new PageRequest(pageNumber, pageSize, Direction.DESC, "id");
		return applyRightRepository.findAll(pageable);
	}

	@Override
	public Page<ApplyRight> findApplyRightByStatusAndApplypermit(Integer status,
			String applypermit, int pageNumber, int pageSize) {
		Pageable pageable = new PageRequest(pageNumber, pageSize, Direction.DESC, "id");
		return applyRightRepository.findApplyRightByStatusAndApplypermit(status, applypermit, pageable);
	}
	@Transactional
	@Override
	public boolean empower4User(Long apply_id, User user,String types) {
		boolean flag = false;
		if(types.equals("person")){
			ApplyRight applyRight = applyRightRepository.findOne(apply_id);
			if(applyRight != null && applyRight.getUser().getId().equals(user.getId())){
				UserRight userRight = new UserRight();
				userRight.setUser(user);
				userRight.setUserRight(applyRight.getApplypermit());
				userRight = userRightRepository.saveAndFlush(userRight);
				if(userRight.getUserRight().equals(applyRight.getApplypermit())){
					flag = true;
				}
			}
		}else if(types.equals("company")){
			CoApplyRight coApplyRight = coApplyRightRepository.findOne(apply_id);
			if(coApplyRight != null && coApplyRight.getUser().getId().equals(user.getId())){
				UserRight userRight = new UserRight();
				userRight.setUser(user);
				userRight.setUserRight(coApplyRight.getComApplypermit());
				userRight = userRightRepository.saveAndFlush(userRight);
				if(userRight.getUserRight().equals(coApplyRight.getComApplypermit())){
					flag = true;
				}
			}
		}
		
	
		return flag;
	}
	

	@Override
	public Page<ApplyRight> findApplyRightByStatus(Integer status,
			int pageNumber, int pageSize) {
		Pageable pageable = new PageRequest(pageNumber, pageSize, Direction.DESC, "id");
		return applyRightRepository.findApplyRightByStatus(status, pageable);
	}

}
