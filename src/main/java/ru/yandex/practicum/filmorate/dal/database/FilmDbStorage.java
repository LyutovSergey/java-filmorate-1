package ru.yandex.practicum.filmorate.dal.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.filmorate.dal.FilmStorage;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.yandex.practicum.filmorate.dal.database.sql.FilmQueryes.*;

@Repository
@Qualifier("filmDbStorage")
public class FilmDbStorage extends BaseDbStorage<Film> implements FilmStorage {

	@Autowired
	public FilmDbStorage(
			JdbcTemplate jdbc,
			@Qualifier("filmRowMapper") RowMapper<Film> mapper
	) {
		super(jdbc, mapper);
	}

	@Override
	public Collection<Film> findAll() {
		return findManyFilms(SQL_FILMS_FIND_ALL);
	}

	@Override
	public Collection<Film> getTop(int top) {
		return findManyFilms(SQL_FILMS_FIND_TOP, top);
	}

	@Override
	public void setLike(long filmId, long userId) {
		insert(SQL_FILMS_SET_LIKE, filmId, userId);
	}

	@Override
	public void removeLike(long filmId, long userId) {
		delete(SQL_FILMS_DELETE_LIKE, filmId, userId);
	}

	@Override
	@Transactional
	public Optional<Film> findById(long filmId) {
		Optional<Film> filmOptional = findByIdInTable(filmId, "films");
        filmOptional.ifPresent(film -> film.setGenreIds(new HashSet<>(getGenreIdsByFilmId(filmId))));

		return filmOptional;
	}

	@Override
	@Transactional
	public Film createFilm(Film film) {
		long id = insert(
				SQL_FILMS_INSERT,
				film.getName(),
				film.getDescription(),
				film.getReleaseDate(),
				film.getDuration().toMinutes(),
				film.getMpaId()
		);

		film.setId(id);
		insertGenreIds(film);
		return film;
	}

	private void insertGenreIds(Film film) {
		Set<Integer> genreIds = film.getGenreIds();
		if (genreIds.isEmpty()) {
			return;
		}
		String placeholders = String.join(",",
				Collections.nCopies(genreIds.size(), " (" + film.getId() + ", ?)")
		);
		String sql = "INSERT INTO GENRES_OF_FILMS (FILM_ID, GENRE_ID) VALUES" + placeholders;
		jdbc.update(sql, genreIds.toArray());
	}

	@Override
	public Film updateFilm(Film film) {
		update(
				SQL_FILMS_UPDATE,
				film.getName(),
				film.getDescription(),
				film.getReleaseDate(),
				film.getDuration().toMinutes(),
				film.getMpaId(),
				film.getId()
		);
		return film;
	}

	@Override
	public Collection<Long> getAllIds() {
		return findIdsOfLong(SQL_FILMS_FIND_ALL_IDS);
	}

	@Override
	public void reset() {
		update(SQL_FILMS_RESET_DATA);
	}

	private Collection<Film> findManyFilms(String query, Object... params) {
		return jdbc.query(query, rs -> {
			Map<Long, Film> films = new LinkedHashMap<>();
			AtomicInteger rowNum = new AtomicInteger();
			while (rs.next()) {
				long filmId = rs.getLong("id");
				Film film = films.computeIfAbsent(filmId, id -> {

					try {
						return rowMapper.mapRow(rs, rowNum.getAndIncrement());
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
				});

				long userId = rs.getLong("user_id");
				if (!rs.wasNull() && film != null) {
					film.addLike(userId);
				}

				int genreId = rs.getInt("genre_id");
				if (!rs.wasNull() && film != null) {
					film.addGenreId(genreId);
				}
			}

			return new ArrayList<>(films.values());
		}, params);
	}

	private Collection<Integer> getGenreIdsByFilmId(long filmId) {
		return findIdsOfInt(SQL_FILMS_FIND_GENREIDS_BY_FILM_ID, filmId);
	}
}
