DROP ALL OBJECTS;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_name varchar(50),
    login varchar(50) NOT NULL,
    email varchar(50) NOT NULL,
    birthday date NOT NULL
);

CREATE TABLE IF NOT EXISTS friends (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    friend_id BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS films (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    film_name varchar(50) NOT NULL,
    description varchar(200) NOT NULL,
    release_date date NOT NULL,
    duration INTEGER NOT NULL,
    mpa_id INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS genres (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    genre_name varchar(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS genres_of_films  (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    film_id BIGINT NOT NULL,
    genre_id INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS mpa (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    mpa_name varchar(5) NOT NULL
);

CREATE TABLE IF NOT EXISTS likes (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    film_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_users_user_name ON users (user_name);
CREATE UNIQUE INDEX IF NOT EXISTS idx_users_email ON users (email);
CREATE UNIQUE INDEX IF NOT EXISTS idx_users_login ON users (login);

CREATE INDEX IF NOT EXISTS idx_friends_user_id ON friends (user_id);
CREATE INDEX IF NOT EXISTS idx_friends_friend_id ON friends (friend_id);

CREATE INDEX IF NOT EXISTS idx_films_film_name ON films (film_name);

ALTER table friends ADD constraint IF NOT EXISTS fk_friends_user_id
FOREIGN KEY(user_id) REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE friends ADD CONSTRAINT IF NOT EXISTS fk_friends_friend_id
FOREIGN KEY(friend_id) REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE films ADD CONSTRAINT IF NOT EXISTS fk_films_mpa_id
FOREIGN KEY(mpa_id) REFERENCES mpa (id) ON DELETE CASCADE;

ALTER TABLE genres_of_films ADD CONSTRAINT IF NOT EXISTS fk_genres_of_films_genre_id
FOREIGN KEY(genre_id) REFERENCES genres (id) ON DELETE CASCADE;

ALTER TABLE genres_of_films ADD CONSTRAINT IF NOT EXISTS fk_genres_of_films_film_id
FOREIGN KEY(film_id) REFERENCES films (id) ON DELETE CASCADE;

ALTER TABLE likes ADD CONSTRAINT IF NOT EXISTS fk_likes_user_id
FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE likes ADD CONSTRAINT IF NOT EXISTS fk_likes_film_id
FOREIGN KEY(film_id) REFERENCES films (id) ON DELETE CASCADE;

