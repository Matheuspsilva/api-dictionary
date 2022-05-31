package com.matheussilvadev.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.matheussilvadev.model.Words;
import com.matheussilvadev.repository.WordsRepository;
import com.matheussilvadev.service.WordsService;


@RestController
public class WordsController {
	
	@Autowired
	private WordsRepository wordsRepository;
	
    @Autowired
    WordsService service;
	
  @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
  public String greetingText() {
      return "\"message\": \"Fullstack Challenge 🏅 - Dictionary\"";
  }
  
  @GetMapping(value = "/x", produces = "application/json")
  public ResponseEntity<List<String>> listWords(@RequestParam(value = "search") String search) {
	  
		List<Words> words = (List<Words>) wordsRepository.findWordsByName(search);
		List<String> wordName = new ArrayList<String>();
		for (Words word : words) {
			wordName.add(word.getWord()) ;
		}

		return new ResponseEntity<List<String>>(wordName, HttpStatus.OK);
	  
  }
  
  @GetMapping("/entries/en")
  public Page<Words> search(
          @RequestParam("search") String searchTerm,
          @RequestParam(
                  value = "page",
                  required = false,
                  defaultValue = "0") int page,
          @RequestParam(
                  value = "limit",
                  required = false,
                  defaultValue = "10") int size) {
	  
      return service.search(searchTerm, page, size);

  }

}
