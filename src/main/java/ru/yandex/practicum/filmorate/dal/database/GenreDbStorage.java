package ru.yandex.practicum.filmorate.dal.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.GenreStorage;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.*;

@Repository
@Qualifier("genreDbStorage")
public class GenreDbStorage extends BaseDbStorage<Genre> implements GenreStorage {

	@Autowired
	public GenreDbStorage(JdbcTemplate jdbc, @Qualifier("genreRowMapper") RowMapper<Genre> rowMapper) {
		super(jdbc, rowMapper);
	}

	@Override
	public Optional<Genre> getGenreById(long genreId) {
		return findByIdInTable(genreId, "genres");
	}

	@Override
	public Collection<Genre> getAllGenres() {
		return findAllInTable("genres");
	}

	@Override
	public Set<Genre> getGenresByGenreIds(Set<Integer> genreIds) {
		String placeholders = String.join(",", Collections.nCopies(genreIds.size(), "?"));
		return new HashSet<>(
				findMany(
						"SELECT * FROM GENRES WHERE id IN (" + placeholders + ")",
						genreIds.toArray()
				)
		);
	}

	@Override
	public Set<Integer> getAllGenreIds() {
		return new HashSet<>(findAllIntIdsInTable("genres"));
	}

	@Override
	public Map<Integer, Genre> getMapOfAllGenres() {
		Map<Integer, Genre> map = new HashMap<>();
		 jdbc.query("SELECT * FROM genres", rowMapper)
				.forEach(genre -> map.put(genre.getId(), genre));
		 return map;
	}

}
