package ru.yandex.practicum.filmorate.dal.database.sql;

public class UserQueryes {
	public static final String SQL_USERS_FIND_ALL_IDS = """
			SELECT    id
			FROM      users
			ORDER BY  id;
			""";

	public static final String SQL_USERS_FIND_BY_EMAIL = """
			SELECT    *
			FROM      users
			WHERE     email = ?
			""";

	public static final String SQL_USERS_FIND_BY_ID = """
			SELECT    *
			FROM      users
			WHERE     id = ?
			""";

	public static final String SQL_USERS_INSERT = """
			INSERT    INTO users (user_name, login, email, birthday)
			VALUES    (?, ?, ?, ?)
			""";

	public static final String SQL_USERS_UPDATE = """
			UPDATE    users
			SET       user_name = ?,
			          login = ?,
			          email = ?,
			          birthday = ?
			WHERE     id = ?
			""";

	public static final String SQL_USERS_ADD_FRIENDS = """
			INSERT    INTO friends (user_id, friend_id)
			VALUES    (?, ?)
			""";

	public static final String SQL_USERS_DELETE_FRIENDS = """
			DELETE    FROM friends
			WHERE     user_id = ?
			AND       friend_id = ?
			""";

	public static final String SQL_USERS_FIND_FRIEND_IDS = """
			SELECT    friend_id
			FROM      friends
			WHERE     user_id = ?
			""";

	public static final String SQL_USERS_FIND_ALL_USERS_AND_FRIENDS = """
			SELECT    u.id         AS user_id,
			          u.user_name  AS user_name,
			          u.login      AS login,
			          u.email      AS email,
			          u.birthday   AS birthday,
			          f.friend_id  AS friend_id
			FROM      users u
			LEFT JOIN friends f ON u.id = f.user_id
			ORDER BY  user_id
			""";

	public static final String SQL_USERS_RESET_DATA = """
			DELETE    FROM users;
			
			ALTER     TABLE users
			ALTER     COLUMN id
			RESTART   WITH 1;
			""";
}
