package com.matheussilvadev.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.matheussilvadev.model.Words;
import com.matheussilvadev.model.AccessedWordDTO;
import com.matheussilvadev.model.FavoriteWordsDTO;

@Repository
public interface WordsRepository extends CrudRepository<Words, Long> {

	@Query("select w from Words w where w.word = ?1")
	Words findWordsByName(String word);

	@Query("select w.word from Words w where w.word like :searchTerm%")
	Page<Words> search(@Param("searchTerm") String searchTerm, Pageable pageable);
	
	@Query(value = "SELECT word, accessed_at FROM words JOIN words_access ON words_access.word_id = words.id JOIN api_user ON api_user.id = words_access.user_id WHERE api_user.id = ?1"
	, nativeQuery=true)
	Page<AccessedWordDTO>findAcessedWordsByUser(Long userId,  Pageable pageable);
	
	@Query(value = "SELECT word, accessed_at FROM words JOIN favorite_words ON favorite_words.word_id = words.id JOIN api_user ON api_user.id = favorite_words.user_id WHERE api_user.id = ?1"
	, nativeQuery=true)
	Page<FavoriteWordsDTO>findFavoritesWordsByUser(Long userId,  Pageable pageable);
	

}
