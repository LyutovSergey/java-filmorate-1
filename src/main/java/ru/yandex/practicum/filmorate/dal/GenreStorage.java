package ru.yandex.practicum.filmorate.dal;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface GenreStorage {
	Optional<Genre> getGenreById(long genreId);

	Collection<Genre> getAllGenres();

	Map<Integer, Genre> getMapOfAllGenres();
}
