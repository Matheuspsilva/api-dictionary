package com.matheussilvadev.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.matheussilvadev.model.ApiUser;

public interface ApiUserRepository extends CrudRepository<ApiUser, Long> {

	@Query("select u from ApiUser u where u.login = ?1")
	ApiUser findUserByLogin(String id);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "update api_user set token  = ?1 where login = ?2")
	void atualizaTokenUser( String token, String login);
	
}
