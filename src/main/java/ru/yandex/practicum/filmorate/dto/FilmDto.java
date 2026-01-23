package ru.yandex.practicum.filmorate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
@JsonPropertyOrder
public class FilmDto {
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private long id;
	private String name;
	private String description;
	private LocalDate releaseDate;
	private Duration duration;

	@JsonProperty("rate")
	int likesCount;

	@Builder.Default
	private Mpa mpa = Mpa.builder().build();

	@Builder.Default
	private Collection<Genre> genres = new ArrayList<>();
}
