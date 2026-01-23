package ru.yandex.practicum.filmorate.dal;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface GenreStorage {
	Optional<Genre> getGenreById(long genreId);

	Collection<Genre> getAllGenres();

	Set<Genre> getGenresByGenreIds(Set<Integer> genreIds);

	Set<Integer> getAllGenreIds();

	Map<Integer, Genre> getMapOfAllGenres();
}
