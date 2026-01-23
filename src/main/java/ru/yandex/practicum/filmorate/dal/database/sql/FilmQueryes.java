package ru.yandex.practicum.filmorate.dal.database.sql;

public class FilmQueryes {

	public static final String SQL_FILMS_FIND_ALL = """
			SELECT    mrg.id,
			          mrg.FILM_NAME,
			          mrg.DESCRIPTION,
			          mrg.RELEASE_DATE,
			          mrg.DURATION,
			          mrg.MPA_ID,
			          gof.GENRE_ID,
			          user_id
			FROM      (
			          SELECT    f.ID,
			                    f.FILM_NAME,
			                    DESCRIPTION,
			                    RELEASE_DATE,
			                    DURATION,
			                    MPA_ID,
			                    l.USER_ID
			          FROM      FILMS f
			          LEFT JOIN LIKES l ON f.ID = l.FILM_ID
			          ORDER BY  f.ID
			          ) AS mrg
			LEFT JOIN GENRES_OF_FILMS gof ON mrg.id = gof.FILM_ID;
			""";

	public static final String SQL_FILMS_FIND_TOP = """
			SELECT    mrg.id,
			          mrg.FILM_NAME,
			          mrg.DESCRIPTION,
			          mrg.RELEASE_DATE,
			          mrg.DURATION,
			          mrg.MPA_ID,
			          gof.GENRE_ID,
			          user_id
			FROM      (
			          SELECT    f.ID,
			                    f.FILM_NAME,
			                    DESCRIPTION,
			                    RELEASE_DATE,
			                    DURATION,
			                    MPA_ID,
			                    l.USER_ID
			          FROM      FILMS f
			          LEFT JOIN LIKES l ON f.ID = l.FILM_ID
			          ORDER BY  f.ID
			          ) AS mrg
			LEFT JOIN GENRES_OF_FILMS gof ON mrg.id = gof.FILM_ID
			WHERE     mrg.id IN (
			          SELECT    rte.ID
			          FROM      (
			                    SELECT    flm.ID,
			                              COUNT(lks.*) AS rate
			                    FROM      FILMS flm
			                    LEFT JOIN LIKES lks ON flm.ID = lks.FILM_ID
			                    GROUP BY  flm.ID
			                    ORDER BY  rate DESC,
			                              flm.ID
			                    LIMIT     ?
			                    ) AS rte
			          );
			""";

	public static final String SQL_FILMS_SET_LIKE = """
			INSERT    INTO LIKES (FILM_ID, USER_ID)
			VALUES    (?, ?);
			""";

	public static final String SQL_FILMS_DELETE_LIKE = """
			DELETE    FROM LIKES
			WHERE     film_id = ?
			AND       user_id = ?;
			""";

	public static final String SQL_FILMS_FIND_ALL_IDS = """
			SELECT    id
			FROM      films
			ORDER BY  id;
			""";

	public static final String SQL_FILMS_FIND_GENREIDS_BY_FILM_ID = """
			SELECT    g.ID
			FROM      GENRES g
			JOIN      GENRES_OF_FILMS gof ON g.ID = gof.GENRE_ID
			WHERE     gof.FILM_ID = ?
			""";

	public static final String SQL_FILMS_INSERT = """
			INSERT INTO films (
			       film_name,
			       description,
			       release_date,
			       duration,
			       mpa_id
			       )
			VALUES (?, ?, ?, ?, ?)
			""";

	public static final String SQL_FILMS_UPDATE = """
			UPDATE films
			   SET film_name = ?,
			       description = ?,
			       release_date = ?,
			       duration = ?,
			       mpa_id = ?
			 WHERE id = ?
			""";

	public static final String SQL_FILMS_RESET_DATA = """
			DROP      INDEX idx_films_film_name_release_date;
			DELETE    FROM film;
			DELETE    FROM genres_of_films;
			ALTER     TABLE film
			ALTER     COLUMN id
			RESTART   WITH 1;
			CREATE    UNIQUE INDEX idx_films_film_name_release_date ON film (film_name, release_date);
			""";
}
