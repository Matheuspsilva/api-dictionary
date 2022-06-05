package com.matheussilvadev.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matheussilvadev.model.ApiUser;
import com.matheussilvadev.model.ApiUserDTO;
import com.matheussilvadev.repository.ApiUserRepository;

@RestController
@RequestMapping(value = "/usuarios")
public class UserController {
	
	@Autowired
	private ApiUserRepository usuarioRepository;
	
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<ApiUser> usuario(@PathVariable(value = "id") Long id) {
		
		Optional<ApiUser> usuario = usuarioRepository.findById(id);

		return new ResponseEntity<ApiUser>(usuario.get(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<List<ApiUser>> listarUsuarios() throws InterruptedException {
		
		List<ApiUser> usuarios = (List<ApiUser>) usuarioRepository.findAll();

		return new ResponseEntity<List<ApiUser>>(usuarios, HttpStatus.OK);
	}
	
	@PostMapping(value = "/", produces = "application/json")
	public ResponseEntity<ApiUserDTO> cadastrarUsuario(@RequestBody ApiUser usuario) throws Exception {
		
		String senhaCriptografada = new BCryptPasswordEncoder().encode(usuario.getPassword());
		usuario.setPassword(senhaCriptografada);
		
		ApiUser usuarioSalvo = usuarioRepository.save(usuario);

		return new ResponseEntity<ApiUserDTO>(new ApiUserDTO(usuarioSalvo) , HttpStatus.OK);
	}
	
	
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<String> deletarUsuario(@PathVariable(value = "id") Long id) {
		
		usuarioRepository.deleteById(id);

		return new ResponseEntity<String>("Usuário " + id + " deletado com sucesso", HttpStatus.OK);
	}

}
