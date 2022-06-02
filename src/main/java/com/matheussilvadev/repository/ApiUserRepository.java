package com.matheussilvadev.repository;

import org.springframework.data.repository.CrudRepository;

import com.matheussilvadev.model.ApiUser;

public interface ApiUserRepository extends CrudRepository<ApiUser, Long> {

}
