package ru.yandex.practicum.filmorate.dal;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface MpaStorage {

	/// Получить МППА рейтинг по id
	Optional<Mpa> getMpaById(long mpaId);

	/// Получить список всех МППА рейтингов
	Collection<Mpa> getAllMpa();

	/// Получить матрицу МППА рейтингов и их id
	Map<Integer, Mpa> getMapOfAllMpa();
}
