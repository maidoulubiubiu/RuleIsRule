package com.knet51.courses.jpa.services.trade;

import java.util.List;

import org.springframework.data.domain.Page;

import com.knet51.ccweb.jpa.entities.UserOrder;
import com.knet51.ccweb.jpa.entities.User;
import com.knet51.ccweb.jpa.entities.projects.Projects;


public interface OrderService {
	UserOrder findOne(Long id);
	UserOrder createOrder(UserOrder order);
	UserOrder updateOrder(UserOrder order);
	Page<UserOrder> findAll(int pageNumber, int pageSize);
	Page<UserOrder> findOrderByUser(int pageNumber, int pageSize, User user);
	
	List<UserOrder> findOrderByStatusAndProject(String status,Projects project);
}
