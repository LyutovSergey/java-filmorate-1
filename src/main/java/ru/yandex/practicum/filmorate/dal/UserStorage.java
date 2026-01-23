package ru.yandex.practicum.filmorate.dal;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserStorage {

	/// Получить пользователя по id
	Optional<User> findById(long id);

	/// Получить всех пользователей
	Collection<User> findAll();

	/// Добавить нового пользователя
	User createUser(User user);

	/// Обновить данные пользователя
	User updateUser(User user);

	/// Добавить в друзья
	void addToFriends(long userId, long friendId);

	/// Удалить из друзей
	void removeFriends(long userId, long friendId);

	/// Получение id всех пользователей
	Collection<Long> getAllIds();

	/// Получить список id друзей
	Collection<Long> getFriendIds(long userId);

	/// Очистить данные
	void reset();
}
