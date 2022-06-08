package com.matheussilvadev.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.matheussilvadev.service.ImplementacaoUserDetailsService;

//Mapeia URL, endereços, autoriza ou bloqueia acessos à URL
@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private ImplementacaoUserDetailsService implementacaoUserDetailsService;
	
	//Configura as solicitações de acesso por Http
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//Ativando a proteção contra usuários que não estão validados por token
		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
			.disable().authorizeRequests().antMatchers("/").permitAll()//Ativando permissão à página inicial do sistema
			.antMatchers("/index").permitAll() //Ativando permissão à página index do sistema
			.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
			.anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")	//URL de Logout - Redireciona após usuário deslogar do sistema
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))	//Mapeia URL de Logout e invalida o usuário
			.and().addFilterBefore(new JWTLoginFilter("/auth/signin", authenticationManager()), UsernamePasswordAuthenticationFilter.class) //Filtra as requisições de login para autenticação
			.addFilterBefore(new JwtApiAutenticacaoFilter(),UsernamePasswordAuthenticationFilter.class ); //Filtra demais requisições para verificar a presença do token JWT no Header Http

	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//Service responsável pela consulta do usuário no banco de dados
		auth.userDetailsService(implementacaoUserDetailsService)
			.passwordEncoder(new BCryptPasswordEncoder()); //Padrão de codificação de senha
	}
	
}
