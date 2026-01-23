package ru.yandex.practicum.filmorate.dal.database;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import ru.yandex.practicum.filmorate.exception.InternalServerException;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

@RequiredArgsConstructor
public class BaseDbStorage<T> {
    protected final JdbcTemplate jdbc;
    protected final RowMapper<T> rowMapper;

    protected Optional<T> findOne(String query, Object... params) {
        try {
            T result = jdbc.queryForObject(query, rowMapper, params);
            return Optional.ofNullable(result);
        } catch (EmptyResultDataAccessException ignored) {
            return Optional.empty();
        }
    }

    protected Optional<T> findByIdInTable(long id, String tableName) {
        return jdbc.query("SELECT * FROM " + tableName + " WHERE id = ?", rowMapper, id)
                .stream()
                .findFirst();
    }

    protected Collection<T> findAllInTable(String tableName) {
        return jdbc.query("SELECT * FROM " + tableName + " ORDER BY id", rowMapper)
                .stream()
                .toList();
    }

    protected Collection<Long> findAllLongIdsInTable(String tableName) {
        return jdbc.queryForList("SELECT id FROM " + tableName + " ORDER BY id", Long.class)
                .stream()
                .toList();
    }

    protected Collection<Integer> findAllIntIdsInTable(String tableName) {
        return jdbc.queryForList("SELECT id FROM " + tableName + " ORDER BY id", Integer.class)
                .stream()
                .toList();
    }

    protected List<T> findMany(String query, Object... params) {
        return jdbc.query(query, rowMapper, params);
    }

    public void delete(String query, Object... params) {
        jdbc.update(query, params);
    }

    protected long insert(String query, Object... params) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            for (int idx = 0; idx < params.length; idx++) {
                ps.setObject(idx + 1, params[idx]);
            }
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKeyAs(Long.class);
        if (id != null) {
            return id;
        } else {
            throw new InternalServerException("Не удалось сохранить данные");
        }
    }

    protected void insertSimple(String query, Object... params) {
        jdbc.update(query, params);
    }

    protected void update(String query, Object... params) {
        int rowsUpdated = jdbc.update(query, params);
        if (rowsUpdated == 0) {
            throw new InternalServerException("Не удалось обновить данные");
        }
    }

    protected Collection<Long> findIdsOfLong(String query, Object... params) {
        try {
            return jdbc.queryForList(query, Long.class, params);
        } catch (EmptyResultDataAccessException ignored) {
            return new ArrayList<>();
        }
    }

    protected Collection<Integer> findIdsOfInt(String query, Object... params) {
        try {
            return jdbc.queryForList(query, Integer.class, params);
        } catch (EmptyResultDataAccessException ignored) {
            return new ArrayList<>();
        }
    }
}
