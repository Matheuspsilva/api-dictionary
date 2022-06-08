package com.matheussilvadev.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.matheussilvadev.model.AccessedWordDTO;
import com.matheussilvadev.model.ApiUser;
import com.matheussilvadev.model.FavoriteWords;
import com.matheussilvadev.model.FavoriteWordsDTO;
import com.matheussilvadev.model.Words;
import com.matheussilvadev.model.WordsAccess;
import com.matheussilvadev.repository.ApiUserRepository;
import com.matheussilvadev.repository.FavoriteWordsRepository;
import com.matheussilvadev.repository.WordAccessRepository;
import com.matheussilvadev.repository.WordsRepository;
import com.matheussilvadev.service.WordsService;

@RestController
public class WordsController {

	@Autowired
	private WordsRepository wordsRepository;
	
	@Autowired
	private WordAccessRepository wordAccessRepository;
	
	@Autowired
	private FavoriteWordsRepository favoriteWordsRepository;
	
	@Autowired
	private ApiUserRepository apiUserRepository;

	@Autowired
	WordsService service;

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<String> index() {
		return new ResponseEntity<String>("{\"message\": \"Fullstack Challenge 🏅 - Dictionary\"}", HttpStatus.OK);
	}

	@GetMapping(value = "/entries/en/{word}", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> searchWord(@PathVariable(value = "word") String wordName) throws Exception {
		
		//Seleciona Usuario logado
		String userDetails = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ApiUser usuarioSelecionado = apiUserRepository.findUserByLogin(userDetails);
		
		//Selecionar palavra pesquisada
		Words word = wordsRepository.findWordsByName(wordName);
		
		//Configurar relação
		WordsAccess wordAcess = new WordsAccess(usuarioSelecionado, word, LocalDateTime.now());
		
		wordAccessRepository.save(wordAcess);
		
		//Consumindo API Externa
		URL url = new URL("https://api.dictionaryapi.dev/api/v2/entries/en/" + wordName );
		URLConnection connection = url.openConnection();
		InputStream is = connection.getInputStream();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		
		String aux = "";
		StringBuilder jsonWord = new StringBuilder();
		
		while((aux = br.readLine()) != null) {
			jsonWord.append(aux);
		}
		
		//Fim do consumo

//		return new ResponseEntity<Words>(word, HttpStatus.OK);
		return new ResponseEntity<String>(jsonWord.toString(), HttpStatus.OK);
		
	}
	
	@PostMapping(value = "/entries/en/{word}/favorite", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Words> favoriteWord(@PathVariable(value = "word") String wordName) {
		
		//Seleciona Usuario logado
		String userDetails = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ApiUser usuarioSelecionado = apiUserRepository.findUserByLogin(userDetails);
		
		//Selecionar palavra pesquisada
		Words word = wordsRepository.findWordsByName(wordName);
		
		//Configurar relação
		FavoriteWords favoriteWord = new FavoriteWords(usuarioSelecionado, word, LocalDateTime.now());
		
		favoriteWordsRepository.save(favoriteWord);
		
		return new ResponseEntity<Words>(word, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/entries/en/{word}/unfavorite", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Words> unfavoriteWord(@PathVariable(value = "word") String wordName) {
		
		//Seleciona Usuario logado
		String userDetails = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ApiUser usuarioSelecionado = apiUserRepository.findUserByLogin(userDetails);
		
		//Selecionar palavra pesquisada
		Words word = wordsRepository.findWordsByName(wordName);
		
		//Configurar relação
		FavoriteWords favoriteWord = new FavoriteWords(usuarioSelecionado, word);
		
		favoriteWordsRepository.delete(favoriteWord);
		
		return new ResponseEntity<Words>(word, HttpStatus.OK);
	}

	@GetMapping("/entries/en")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Page<Words>> search(@RequestParam("search") Optional<String> searchTerm,
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "limit", required = false, defaultValue = "10") int size) {
		
		if(!searchTerm.isPresent()) {
			return new ResponseEntity<Page<Words>>(service.findAll(page, size), HttpStatus.OK);
		}

		return new ResponseEntity<Page<Words>>(service.search(searchTerm.get(), page, size), HttpStatus.OK);
		

	}
	
	@GetMapping(value = "/user/me/history", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Page<AccessedWordDTO>> history(
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "limit", required = false, defaultValue = "10") int size
			) {
		
		//Seleciona Usuario logado
		String userDetails = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ApiUser usuarioSelecionado = apiUserRepository.findUserByLogin(userDetails);
		
		return new ResponseEntity<Page<AccessedWordDTO>>(service.searchAcessedWords(usuarioSelecionado.getId(), page, size), HttpStatus.OK);

	}
	
	@GetMapping(value = "/user/me/favorites", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Page<FavoriteWordsDTO>> favorites(
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "limit", required = false, defaultValue = "10") int size
			
			) {
		
		//Seleciona Usuario logado
		String userDetails = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ApiUser usuarioSelecionado = apiUserRepository.findUserByLogin(userDetails);
		
		return new ResponseEntity<Page<FavoriteWordsDTO>>(service.searchFavoritesWords(usuarioSelecionado.getId(), page, size), HttpStatus.OK);

	}
	
	

}
