package ru.yandex.practicum.filmorate.dal.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.GenreStorage;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@Qualifier("genreDbStorage")
public class GenreDbStorage extends BaseDbStorage<Genre> implements GenreStorage {

	@Autowired
	public GenreDbStorage(JdbcTemplate jdbc, @Qualifier("genreRowMapper") RowMapper<Genre> rowMapper) {
		super(jdbc, rowMapper);
	}

	@Override
	public Optional<Genre> getGenreById(long genreId) {
		return findOneByIdInTable(genreId, "genres");
	}

	@Override
	public Collection<Genre> getAllGenres() {
		return findAllInTable("genres");
	}

	@Override
	public Map<Integer, Genre> getMapOfAllGenres() {
		Map<Integer, Genre> allGenres = new HashMap<>();
		findAllInTable("genres")
				.forEach(genre -> allGenres.put(genre.getId(), genre));
		return allGenres;
	}

}
