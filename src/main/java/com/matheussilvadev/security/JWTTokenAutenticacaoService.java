package com.matheussilvadev.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.matheussilvadev.ApplicationContextLoad;
import com.matheussilvadev.model.ApiUser;
import com.matheussilvadev.repository.ApiUserRepository;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Component
public class JWTTokenAutenticacaoService {

	// Tempo de expiração do token: 2 dias
	private static final long EXPIRATION_TIME = 172800000;

	// Senha única para compor autenticação e ajudar na segurança
	private static final String SECRET = "senhaExtremamenteSecreta";

	// Pŕefixo padrão de Token
	private static final String TOKEN_PREFIX = "Bearer";

	// Identificação do cabeçalho da resposta
	private static final String HEADER_STRING = "Authorization";

	// Gerando token de autenticação e adicionando ao cabeçalho e resposta Http
	public void addAuthentication(HttpServletResponse response, String username) throws IOException {
		// Montagem do Token
		String JWT = Jwts.builder()// Chama o gerador de token
				.setSubject(username) // Adiciona o usuário
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))// Tempo de expiração
				.signWith(SignatureAlgorithm.HS512, SECRET).compact(); // Compactação e algorítimo de geração de senha

		// Junta o token com o prefixo
		String token = TOKEN_PREFIX + " " + JWT; // Bearer luJygj93kasasd ...

		// Adiciona no cabeçalho Http
		response.addHeader(HEADER_STRING, token); // Authorization: Bearer luJygj93kasasd ...
		
		ApplicationContextLoad.getApplicationContext()
				.getBean(ApiUserRepository.class).atualizaTokenUser(JWT, username);
		
		liberacaoCors(response);

		// Escreve token como resposta no corpo http
//		response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
		response.getWriter().write("{\"name\": \"" + username + "\"");
		response.getWriter().write(",\"token\": \"" + token + "\"}");

	}

	// Retorna o usuário validado com token ou, caso não seja válido retorna null
	public Authentication getAuthentication (HttpServletRequest request, HttpServletResponse response) {
		
		//Pega o token enviado no cabeçalho Http
		String token = request.getHeader(HEADER_STRING);
		
		try {
			if(token != null) {
				
				String tokenLimpo = token.replace(TOKEN_PREFIX, "").trim();
				
				//Faz a validação do token do usuário na requisição
				String user = Jwts.parser().setSigningKey(SECRET) //Bearer luJygj93kasasd ...
						.parseClaimsJws(tokenLimpo)//luJygj93kasasd ...
						.getBody().getSubject(); //Usuário
				if(user != null) {
					ApiUser usuario = ApplicationContextLoad.getApplicationContext()
							.getBean(ApiUserRepository.class).findUserByLogin(user);
					
					
					//Retorna o usuário logado
					if(usuario != null) {
						if(tokenLimpo.equalsIgnoreCase(usuario.getToken())) {
							return new UsernamePasswordAuthenticationToken(usuario.getLogin(), usuario.getPassword(), usuario.getAuthorities());
						}
					}
				}
			}
		} catch (ExpiredJwtException e) {
			try {
				response.getOutputStream().println("Seu token está espirado, faça o login ou informe um novo token para autenticação");
			} catch (IOException e1) {
				
			}
		}

		
		liberacaoCors(response);
		return null;//Não Autorizado
	}

	private void liberacaoCors(HttpServletResponse response) {
		if (response.getHeader("Access-Control-Allow-Origin") == null) {
			response.addHeader("Access-Control-Allow-Origin", "*");
		}
		
		if (response.getHeader("Access-Control-Allow-Headers") == null) {
			response.addHeader("Access-Control-Allow-Headers", "*");
		}
		
		
		if (response.getHeader("Access-Control-Request-Headers") == null) {
			response.addHeader("Access-Control-Request-Headers", "*");
		}
		
		if(response.getHeader("Access-Control-Allow-Methods") == null) {
			response.addHeader("Access-Control-Allow-Methods", "*");
		}
		
	}

}
