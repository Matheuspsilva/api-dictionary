package com.matheussilvadev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EntityScan(basePackages = {"com.matheussilvadev.model"})
@ComponentScan(basePackages = {"com.matheussilvadev"}) 
@EnableJpaRepositories(basePackages = {"com.matheussilvadev.repository"})
@EnableTransactionManagement 
@EnableWebMvc 
@RestController 
@EnableAutoConfiguration 
public class ApiDictionaryApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(ApiDictionaryApplication.class, args);
		System.out.println(new BCryptPasswordEncoder().encode("1234"));
	}
	
	//Mapeamento Global CrossOrigins
		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**")
				.allowedMethods("*")
				.allowedOrigins("*");
		}

}
