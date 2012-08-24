package com.knet51.ccweb.jpa.core;

import org.springframework.data.repository.Repository;



/**
 * {@link Repository} to access {@link User} instances.
 * 
 * @author
 */
public interface UserRepository extends Repository<User, Long> {

	/**
	 * Returns the {@link User} with the given identifier.
	 * 
	 * @param id the id to search for.
	 * @return
	 */
	User findOne(Long id);

	/**
	 * Saves the given {@link User}.
	 * 
	 * @param User the {@link User} to search for.
	 * @return
	 */
	User save(User User);

	/**
	 * Returns the User with the given {@link EmailAddress}.
	 * 
	 * @param emailAddress the {@link EmailAddress} to search for.
	 * @return
	 */
	User findByEmailAddress(EmailAddress emailAddress);
}