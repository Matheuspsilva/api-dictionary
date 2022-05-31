package com.matheussilvadev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

//    public Page<Words> findAll() {
//        int page = 0;
//        int size = 10;
//        PageRequest pageRequest = PageRequest.of(
//                page,
//                size,
//                Sort.Direction.ASC,
//                "name");
//        return new PageImpl<>(
//                repository.findAll(), 
//                pageRequest, size);
//    }
	
	

}
