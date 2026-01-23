package ru.yandex.practicum.filmorate.dal;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface MpaStorage {
	Optional<Mpa> getMpaById(long mpaId);

	Collection<Mpa> getAllMpa();

	Map<Integer, Mpa> getMapOfAllMpa();
}
