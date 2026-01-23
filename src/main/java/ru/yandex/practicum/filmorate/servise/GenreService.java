package ru.yandex.practicum.filmorate.servise;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.database.GenreDbStorage;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreService {
	private final GenreDbStorage genreStorage;

	public Genre getGenreById(long genreId) {
		log.info("Получение жанра с id={}", genreId);
		return genreStorage.getGenreById(genreId)
				.orElseThrow(() -> new NotFoundException("Жанр с id=" + genreId + " не найден."));
	}

	public Collection<Genre> getAllGenres() {
		log.info("Получение всех жанров");
		return genreStorage.getAllGenres();
	}

}
