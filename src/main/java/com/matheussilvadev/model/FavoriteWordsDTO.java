package com.matheussilvadev.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public interface FavoriteWordsDTO {

	public String getWord();

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	public LocalDateTime getAccessed_at();

}
