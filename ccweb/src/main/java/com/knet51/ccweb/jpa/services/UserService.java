package com.knet51.ccweb.jpa.services;

import com.knet51.ccweb.jpa.entities.User;

public interface UserService {
	boolean login(String email, String password);
	User findOne(Long id);
}
