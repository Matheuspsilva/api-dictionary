package com.matheussilvadev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.matheussilvadev.model.AccessedWordDTO;
import com.matheussilvadev.model.FavoriteWordsDTO;
import com.matheussilvadev.model.Words;
import com.matheussilvadev.repository.WordsRepository;

@Service
public class WordsService {
	
	@Autowired
	WordsRepository repository;
	

    public Page<Words> search(
            String searchTerm,
            int page,
            int size) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "word");

        return repository.search(
                searchTerm.toLowerCase(),
                pageRequest);
    }
    
    public Page<AccessedWordDTO> searchAcessedWords(
            Long userId,
            int page,
            int size) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size
                );

        return repository.findAcessedWordsByUser(
                userId,
                pageRequest);
    }
    
    public Page<FavoriteWordsDTO> searchFavoritesWords(
            Long userId,
            int page,
            int size) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size
                );

        return repository.findFavoritesWordsByUser(
                userId,
                pageRequest);
    }
    
  public Page<Words> findAll(
		  int page,
		  int size) {

  PageRequest pageRequest = PageRequest.of(
          page,
          size,
          Sort.Direction.ASC,
          "word");
  return 
          repository.findAllPaginated(pageRequest);
          
}



}
