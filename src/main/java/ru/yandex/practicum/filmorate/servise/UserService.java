package ru.yandex.practicum.filmorate.servise;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.UserStorage;
import ru.yandex.practicum.filmorate.dto.request.create.UserCreateRequest;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.dto.request.update.UserUpdateRequest;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.servise.util.FriendsAction;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

	@Autowired
	@Qualifier("userDbStorage")
	private UserStorage userStorage;

	public void changeFriends(FriendsAction action, long userId, long friendId) {
		switch (action) {
			case REQUEST -> log.info(
					"Запрос в друзья от пользователя id={} к пользователю id={}.", userId, friendId
			);
			case REMOVE -> log.trace(
					"Удаление из друзей пользователя id={} одного пользователя id={}.", userId, friendId
			);
		}

		validateTwoUsers(userId, friendId);
		Collection<Long> friendsOfUser = userStorage.getFriendIds(userId);
		Collection<Long> riendsOfFriend = userStorage.getFriendIds(friendId);

		switch (action) {
			case REQUEST -> {
				if (friendsOfUser.contains(friendId) && riendsOfFriend.contains(userId)) {
					throw new AlredyAcceptedException("Пользователи с id=" + userId +
							" и id=" + friendId + " уже друзья."
					);
				}
				if (friendsOfUser.contains(friendId)) {
					throw new AlredyAcceptedException("Пользователь с id=" + userId +
							" уже отправлял запрос в друзья пользователю с id=" + friendId + "."
					);
				}

				if (riendsOfFriend.contains(userId)) {
					userStorage.addFriend(userId, friendId);
					log.info("Пользователи с id={} и id={} стали друзьями.", userId, friendId);
				}

				userStorage.addFriend(userId, friendId);
			}

			case REMOVE -> {
				if (! friendsOfUser.contains(friendId)) {
					throw new NoContentException();
				}
				userStorage.removeFriend(userId, friendId);
			}
		}
	}

	public UserDto create(UserCreateRequest request) {
		log.info("Добавление нового пользователя login={}.",  request.getLogin());
		User user = UserMapper.mapToUser(request);

		try {
			user = userStorage.createUser(user);
		} catch (DuplicateKeyException e) {
			throw new DuplicatedDataException("Эта электронная почта " + user.getEmail() + " уже используется.");
		}

		return UserMapper.mapToUserDto(user);
	}

	public UserDto update(UserUpdateRequest request) {
		log.info("Обновление данных о пользователе с id={}.", request.getId());

		User oldUser = userStorage.findById(request.getId())
				.orElseThrow(() -> new NotFoundException("Пользователь не найден, обновлять нечего."));

		logUserUpdate(request, oldUser);
		User newUser = UserMapper.updateUserFields(oldUser, request);

		try {
			newUser = userStorage.updateUser(newUser);
		} catch (DuplicateKeyException e) {
			throw new DuplicatedDataException("Эта электронная почта " + newUser.getEmail() + " уже используется.");
		}

		return UserMapper.mapToUserDto(newUser);
	}

	public UserDto findById(long userId) {
		log.info("Поиск пользователя с id={}.", userId);
		return userStorage.findById(userId)
				.map(UserMapper::mapToUserDto)
				.orElseThrow(() -> new NotFoundException("Пользователь не найден с ID: " + userId));
	}

	public Collection<UserDto> findAll() {
		log.info("Поиск всех пользователей.");
		return userStorage.findAll()
				.stream()
				.map(UserMapper::mapToUserDto)
				.collect(Collectors.toList());
	}

	public Collection<UserDto> getListOfFriends(long userId) {
		log.info("Получение списка друзей пользователя id={}.", userId);

		User user = userStorage.findById(userId).orElseThrow(() ->
				new NotFoundException("Пользователь с id=" + userId + " не найден.")
		);

		return user.getFriendsIds().stream()
				.sorted(Long::compareTo)
				.map(userStorage::findById)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.map(UserMapper::mapToUserDto)
				.toList();
	}

	public Collection<UserDto> getListOfMutualFriends(long userId, long friendId) {
		log.info("Получение списка общих друзей у пользователей id={} и id={}.", userId, friendId);

		validateTwoUsers(userId, friendId);
		Collection<Long> friendsOfUser = userStorage.getFriendIds(userId);
		Collection<Long> riendsOfFriend = userStorage.getFriendIds(friendId);

		Collection<Long> mutualFriendTds = friendsOfUser.stream()
				.filter(riendsOfFriend::contains)
				.toList();

		return mutualFriendTds.stream()
				.sorted(Long::compareTo)
				.map(userStorage::findById)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.map(UserMapper::mapToUserDto)
				.toList();
	}

	private void validateTwoUsers(long userId1, long userId2) {
		if (userId1 == userId2) {
			throw new ParameterNotValidException(String.valueOf(userId1), "У пользователей должны быть разные id.");
		}

		if (userStorage.checkUserIsNotPresent(userId1)) {
			throw new NotFoundException("Первый пользователь с id=" + userId1 + " не найден.");
		}

		if (userStorage.checkUserIsNotPresent(userId2)) {
			throw new NotFoundException("Второй пользователь с id=" + userId2 + " не найден.");
		}
	}

	private void logUserUpdate(UserUpdateRequest request, User user) {
		StringBuilder updatedData = new StringBuilder();
		updatedData.append("[");
		if (request.hasNname() && !request.getName().equals(user.getName())) {
			updatedData.append("имя").append(", ");
		}
		if (request.hasEmail() && !request.getEmail().equals(user.getEmail())) {
			updatedData.append("электронная почта").append(", ");
		}
		if (request.hasLogin() && !request.getLogin().equals(user.getLogin())) {
			updatedData.append("логин").append(", ");
		}
		if (request.hasBirthday() && !request.getBirthday().isEqual(user.getBirthday())) {
			updatedData.append("дата рождения").append(", ");
		}
		if (updatedData.length() > 2) {
			updatedData.setLength(updatedData.length() - 2);
		}
		updatedData.append("]");
		log.info("Данные для обновления: {}", updatedData);
	}
}
