package com.matheussilvadev.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.matheussilvadev.model.Words;

@Repository
public interface WordsRepository extends CrudRepository<Words, Long> {

	@Query("select w from Words w where w.word like %?1%")
	List<Words> findWordsByName(String word);

	@Query("select w.word from Words w where w.word like :searchTerm%")
	Page<Words> search(@Param("searchTerm") String searchTerm, Pageable pageable);

}
