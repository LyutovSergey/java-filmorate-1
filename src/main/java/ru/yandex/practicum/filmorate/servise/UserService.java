package ru.yandex.practicum.filmorate.servise;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserStorage userStorage;

	public void addToFriends(Long userId1, Long userId2) {
		log.info("Добавление в друзья пользователей id={} и id={}.", userId1,  userId2);
		User user1 = userStorage.get(userId1);
		User user2 = userStorage.get(userId2);

		user1.addFriendId(user2.getId());
		user2.addFriendId(user1.getId());
	}

	public void removeFromFriends(Long userId1, Long userId2) {
		log.trace("Удаление из друзей пользователей id={} и id={}.", userId1,  userId2);
		User user1 = userStorage.get(userId1);
		User user2 = userStorage.get(userId2);

		user1.removeFriends(user2.getId());
		user2.removeFriends(user1.getId());
	}

	public Collection<User> getListOfFriends(Long userId) {
		log.info("Получение списка друзей пользователя id={}.", userId);
		User user = userStorage.get(userId);

		return userStorage.findAll().stream()
				.filter(u -> user.getFriendsIds().contains(u.getId()))
				.toList();
	}

	public Collection<User> getListOfMutualFriends(Long userId1, Long userId2) {
		log.info("Получение списка общих друзей у пользователей id={} и id={}.", userId1,  userId2);
		User user1 = userStorage.get(userId1);
		User user2 = userStorage.get(userId2);

		return userStorage.findAll().stream()
				.filter(u ->
						user1.getFriendsIds().contains(u.getId()) &&
						user2.getFriendsIds().contains(u.getId())
				).toList();
	}
}
