-- mpas
merge INTO mpa (mpa_name) key (mpa_name)
VALUES  ('G'),      -- 1
        ('PG'),     -- 2
        ('PG-13'),  -- 3
        ('R'),      -- 4
        ('NC-17');  -- 5

-- genres
merge INTO genres (genre_name) key (genre_name)
VALUES  ('Комедия'),         -- 1
        ('Драма'),           -- 2
        ('Мультфильм'),      -- 3
        ('Триллер'),         -- 4
        ('Документальный'),  -- 5
        ('Боевик');          -- 6
