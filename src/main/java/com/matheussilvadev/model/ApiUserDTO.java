package com.matheussilvadev.model;

import java.io.Serializable;

public class ApiUserDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String login;
	
	
	public ApiUserDTO(ApiUser usuario) {
		this.id = usuario.getId();
		this.login = usuario.getLogin();
		this.name = usuario.getName();
	}
	
	public String getUserLogin() {
		return login;
	}
	public void setUserLogin(String userLogin) {
		this.login = userLogin;
	}
	public String getUserNome() {
		return name;
	}
	public void setUserNome(String userNome) {
		this.name = userNome;
	}

	public Long getUserId() {
		return id;
	}

	public void setUserId(Long userId) {
		this.id = userId;
	}

}
