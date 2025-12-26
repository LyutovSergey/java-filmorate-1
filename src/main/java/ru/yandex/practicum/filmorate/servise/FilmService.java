package ru.yandex.practicum.filmorate.servise;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Comparator;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {

	private final FilmStorage filmStorage;
	private final UserStorage userStorage;

	public void setLike(Long filmId, Long userId) {
		log.info("Добавление лайка пользователя id={} к фильму id={}.", userId, filmId);

		Film film = filmStorage.get(filmId);
		User user = userStorage.get(userId);

		film.setLike(user);
	}

	public void unsetLike(Long filmId, Long userId) {
		log.info("Удаление лайка пользователя id={} у фильма id={}.", userId, filmId);

		Film film = filmStorage.get(filmId);
		User user = userStorage.get(userId);

		film.removeLike(user);
	}

	public Collection<Film> getTopFilms(Integer count) {
		log.info("Получение топ{} фильмов.", count);

		return filmStorage.findAll().stream()
				.sorted(Comparator.comparing(Film::getLikesCount).reversed())
				.limit(count)
				.toList();
	}
}
