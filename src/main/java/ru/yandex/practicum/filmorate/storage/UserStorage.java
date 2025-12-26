package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {

	/// Получить пользователя по id
	User get(Long id);

	/// Получить всех пользователей
	Collection<User> findAll();

	/// Добавить нового пользователя
	User create(User user);

	/// Обновить данные пользователя
	User update(User user);
}
