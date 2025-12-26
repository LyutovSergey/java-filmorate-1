package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.servise.IdentifyService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {

	private final Map<Long, Film> films = new HashMap<>();
	private final LocalDate birthdayOfCinema = LocalDate.of(1895, 12, 28);
	private final IdentifyService identifyService = new IdentifyService();

	@Override
	public Film get(Long id) {
		Film film = films.get(id);
		if (film == null) {
			throw new NotFoundException("Film with id " + id + " not found");
		}
		return film;
	}

	@Override
	public Collection<Film> findAll() {
		log.info("Поиск всех фильмов");
		if (films.isEmpty()) {
			throw new NotFoundException("Films not found");
		}
		return films.values();
	}

	@Override
	public Film create(Film film) {
		log.info("Добавление нового фильма:");

		log.trace("Проверка названия нового фильма");
		if (film.getName() == null) {
			throw new ValidationException("Название должно быть указано.");
		}

		log.trace("Проверка даты релиза нового фильма");
		if (film.getReleaseDate() == null) {
			throw new ValidationException("Дата релиза должна быть указана.");
		}

		log.trace("Проверка продолжительности нового фильма");
		if (film.getDuration() == null) {
			throw new ValidationException("Продолжительность фильма должна быть указана.");
		}

		filmValidate(film);

		log.trace("Сохранение нового фильма");
		film.setId(identifyService.getNextId(films));
		films.put(film.getId(), film);
		return film;
	}

	@Override
	public Film update(Film film) {
		log.info("Обновление данных о фильме:");

		if (film == null) {
			throw new ValidationException("Входные данные фильма не распознаны.");
		}

		log.trace("Проверка id фильма");
		if (film.getId() == null) {
			throw new ConditionsNotMetException("Id должен быть указан.");
		} else if (!films.containsKey(film.getId())) {
			throw new NotFoundException("Пользователь с id = " + film.getId() + " не найден.");
		}

		filmValidate(film);

		log.trace("Получение текущих данных о фильме");
		Film oldFilm = films.get(film.getId());

		log.trace("Обработка названия фильма");
		if (film.getName() == null) {
			film.setName(oldFilm.getName());
		} else {
			log.info("Название фильма изменено.");
		}

		log.trace("Обработка описания фильма");
		if (film.getDescription() == null) {
			film.setDescription(oldFilm.getDescription());
		} else {
			log.info("Описание фильма изменено.");
		}

		log.trace("Обработка даты релиза фильма");
		if ((film.getReleaseDate() == null)) {
			film.setReleaseDate(oldFilm.getReleaseDate());
		} else {
			log.info("Дата релиза изменена.");
		}

		log.trace("Обработка продолжительности фильма");
		if (film.getDuration() == null) {
			film.setDuration(oldFilm.getDuration());
		} else {
			log.info("Продолжительность фильма изменена.");
		}

		log.trace("Сохранение новых данных о фильме");
		films.put(film.getId(), film);
		return film;
	}

	private void filmValidate(Film film) {
		log.info("Проверка фильма:");

		log.trace("Проверка названия фильма");
		if (film.getName() != null && film.getName().isBlank()) {
			throw new ValidationException("Название не может быть пустым.");
		}

		log.trace("Проверка описания фильма");
		if (film.getDescription() != null && film.getDescription().length() > 200) {
			throw new ValidationException("Максимальная длина описания — 200 символов.");
		}

		log.trace("Проверка даты релиза фильма");
		if (film.getReleaseDate() != null) {
			if (film.getReleaseDate().isBefore(birthdayOfCinema)) {
				throw new ParameterNotValidException(film.getReleaseDate().toString(),
						"Дата релиза должна быть не раньше 28 декабря 1895 года."
				);
			} else if (film.getReleaseDate().isAfter(LocalDate.now())) {
				throw new ParameterNotValidException(film.getReleaseDate().toString(),
						"Дата релиза не должна быть в будущем."
				);
			}
		}

		log.trace("Проверка продолжительности фильма");
		if (film.getDuration() != null && film.getDuration().toMinutes() <= 0) {
			long minutes = film.getDuration().toMinutes();
			throw new ParameterNotValidException(Long.toString(minutes),
					"Продолжительность фильма должна быть положительным числом."
			);
		}

		log.trace("Проверка дубликатов фильмов");
		if (
				films.values().stream().anyMatch(f ->
						f.getName().equals(film.getName()) &&
								f.getReleaseDate().equals(film.getReleaseDate()))
		) {
			throw new DuplicatedDataException("Фильм с такими названием и датой уже был добавлен ранее");
		}
	}
}
