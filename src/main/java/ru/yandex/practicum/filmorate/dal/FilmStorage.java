package ru.yandex.practicum.filmorate.dal;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmStorage {

	/// Получить фильма по id
	Optional<Film> findById(long id);

	/// Получить всех фильмов
	Collection<Film> findAll();

	/// Добавить нового фильма
	Film createFilm(Film film);

	/// Обновить данные фильма
	Film updateFilm(Film film);

	/// Получение id всех Фильмов
	Collection<Long> getAllIds();

	/// Получить топ фильмов
	Collection<Film> getTop(int count);

	/// Поставить лайк
	void setLike(long filmId, long userId);

	/// Убрать лайк
	void removeLike(long filmId, long userId);

	void reset();
}
