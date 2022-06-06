package com.matheussilvadev.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class FavoriteWordsKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "user_id")
	Long userId;

	@Column(name = "word_id")
	Long wordId;

	
	public FavoriteWordsKey() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FavoriteWordsKey(Long user_id, Long word_id) {
		this.userId = user_id;
		this.wordId = word_id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getWordId() {
		return wordId;
	}

	public void setWordId(Long wordId) {
		this.wordId = wordId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, wordId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FavoriteWordsKey other = (FavoriteWordsKey) obj;
		return Objects.equals(userId, other.userId) && Objects.equals(wordId, other.wordId);
	}
	

}
