package com.matheussilvadev.repository;

import org.springframework.data.repository.CrudRepository;

import com.matheussilvadev.model.FavoriteWords;

public interface FavoriteWordsRepository extends CrudRepository<FavoriteWords, Long> {

}
