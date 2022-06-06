package com.matheussilvadev.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.matheussilvadev.model.ApiUser;
import com.matheussilvadev.model.ApiUserDTO;
import com.matheussilvadev.repository.ApiUserRepository;

@RestController
public class UserController {
	
	@Autowired
	private ApiUserRepository usuarioRepository;
	
	@GetMapping(value = "/users/{id}", produces = "application/json")
	public ResponseEntity<ApiUser> usuario(@PathVariable(value = "id") Long id) {
		
		Optional<ApiUser> usuario = usuarioRepository.findById(id);

		return new ResponseEntity<ApiUser>(usuario.get(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/users", produces = "application/json")
	public ResponseEntity<List<ApiUser>> listarUsuarios() throws InterruptedException {
		
		List<ApiUser> usuarios = (List<ApiUser>) usuarioRepository.findAll();

		return new ResponseEntity<List<ApiUser>>(usuarios, HttpStatus.OK);
	}
	
	@GetMapping(value = "/user/me", produces = "application/json")
	public ResponseEntity<ApiUser> listaUsuarioLogado() throws InterruptedException {
		
		//Seleciona Usuario logado
		String userDetails = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ApiUser usuarioSelecionado = usuarioRepository.findUserByLogin(userDetails);

		return new ResponseEntity<ApiUser>(usuarioSelecionado, HttpStatus.OK);
	}
	
	@PostMapping(value = "/user", produces = "application/json")
	public ResponseEntity<ApiUserDTO> cadastrarUsuario(@RequestBody ApiUser usuario) throws Exception {
		
		String senhaCriptografada = new BCryptPasswordEncoder().encode(usuario.getPassword());
		usuario.setPassword(senhaCriptografada);
		
		ApiUser usuarioSalvo = usuarioRepository.save(usuario);

		return new ResponseEntity<ApiUserDTO>(new ApiUserDTO(usuarioSalvo) , HttpStatus.OK);
	}
	
	
	@DeleteMapping(value = "/user/{id}", produces = "application/json")
	public ResponseEntity<String> deletarUsuario(@PathVariable(value = "id") Long id) {
		
		usuarioRepository.deleteById(id);

		return new ResponseEntity<String>("Usuário " + id + " deletado com sucesso", HttpStatus.OK);
	}

}
