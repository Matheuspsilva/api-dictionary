package com.matheussilvadev.model;

import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class WordsAccess {

    @EmbeddedId
    private WordsAccessKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private ApiUser user;

    @ManyToOne
    @MapsId("wordId")
    @JoinColumn(name = "word_id")
    private Words word;

    private LocalDateTime accessed_at;

	public WordsAccess() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WordsAccess(ApiUser user, Words word, LocalDateTime accessed_at) {
		super();
		this.id = new WordsAccessKey(user.getId(), word.getId());
		this.user = user;
		this.word = word;
		this.accessed_at = accessed_at;
	}

	public WordsAccessKey getId() {
		return id;
	}

	public void setId(WordsAccessKey id) {
		this.id = id;
	}

	public ApiUser getUser() {
		return user;
	}

	public void setUser(ApiUser user) {
		this.user = user;
	}

	public Words getWord() {
		return word;
	}

	public void setWord(Words word) {
		this.word = word;
	}

	public LocalDateTime getAccessed_at() {
		return accessed_at;
	}

	public void setAccessed_at(LocalDateTime accessed_at) {
		this.accessed_at = accessed_at;
	}

	
    
}
