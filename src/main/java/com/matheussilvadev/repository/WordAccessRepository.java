package com.matheussilvadev.repository;

import org.springframework.data.repository.CrudRepository;

import com.matheussilvadev.model.WordsAccess;

public interface WordAccessRepository extends CrudRepository<WordsAccess, Long> {

}
