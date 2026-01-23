package ru.yandex.practicum.filmorate.dal.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.MpaStorage;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@Qualifier("mpaDbStorage")
public class MpaDbStorage extends BaseDbStorage<Mpa> implements MpaStorage {

	@Autowired
	public MpaDbStorage(JdbcTemplate jdbc, @Qualifier("mpaRowMapper") RowMapper<Mpa> rowMapper) {
		super(jdbc, rowMapper);
	}

	@Override
	public Optional<Mpa> getMpaById(long mpaId) {
		return findByIdInTable(mpaId, "mpa");
	}

	@Override
	public Collection<Mpa> getAllMpa() {
		return findAllInTable("mpa");
	}

	@Override
	public Map<Integer, Mpa> getMapOfAllMpa() {
		Map<Integer, Mpa> map = new HashMap<>();
		jdbc.query("SELECT * FROM mpa", rowMapper)
				.forEach(mpa -> map.put(mpa.getId(), mpa));
		return map;
	}

}
