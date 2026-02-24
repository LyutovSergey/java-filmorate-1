package ru.yandex.practicum.filmorate.dal;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface GenreStorage {

	/// Получить жанр по id
	Optional<Genre> getGenreById(long genreId);

	/// Получить список всех жанров
	Collection<Genre> getAllGenres();

	/// Получить матрици всех жанров с их id
	Map<Integer, Genre> getMapOfAllGenres();
}
