package ru.yandex.practicum.filmorate.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.dto.request.create.UserCreateRequest;
import ru.yandex.practicum.filmorate.dto.request.update.UserUpdateRequest;
import ru.yandex.practicum.filmorate.model.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserMapper {
	public static User mapToUser(UserCreateRequest request) {
		return User.builder()
				.name(request.getName() == null ? request.getLogin() : request.getName())
				.login(request.getLogin())
				.email(request.getEmail())
				.birthday(request.getBirthday())
				.build();
	}

	public static UserDto mapToUserDto(User user) {
		return UserDto.builder()
				.id(user.getId())
				.name(user.getName())
				.login(user.getLogin())
				.email(user.getEmail())
				.birthday(user.getBirthday())
				.friends(user.getFriendsIds())
				.build();
	}

	public static User updateUserFields(User user, UserUpdateRequest request) {

		if (request.hasNname()) {
			user.setName(request.getName());
		}

		if (request.hasLogin()) {
			user.setLogin(request.getLogin());
		}

		if (request.hasEmail()) {
			user.setEmail(request.getEmail());
		}

		if (request.hasBirthday()) {
			user.setBirthday(request.getBirthday());
		}

		return user;
	}
}
