package com.matheussilvadev.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Words {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String word;
	
	@JsonIgnore
	@OneToMany(mappedBy = "word")
	private List<WordsAccess> accesses = new ArrayList<WordsAccess>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public List<WordsAccess> getAccesses() {
		return accesses;
	}

	public void setAccesses(List<WordsAccess> accesses) {
		this.accesses = accesses;
	}
	
	
	
}
