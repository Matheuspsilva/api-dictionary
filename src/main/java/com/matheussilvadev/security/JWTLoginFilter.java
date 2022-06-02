package com.matheussilvadev.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matheussilvadev.model.ApiUser;

//Estabelece o gerenciador de Token
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter{

	//Configurando o gerenciador de autenticacao
	protected JWTLoginFilter(String url, AuthenticationManager authenticationManager) {
		
		//Obriga a autenticar  a URL
		super(new AntPathRequestMatcher(url));
		
		setAuthenticationManager(authenticationManager);
		
	}
	
	//Retorna usuário ao processar autenticação
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		// Está pegando o token para validar
		ApiUser user = new ObjectMapper()
				.readValue(request.getInputStream(), ApiUser.class);
		
		//Retorna o usuario login, senha e acesso
		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword()));
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		new JWTTokenAutenticacaoService().addAuthentication(response, authResult.getName());
		
		
	}

}
