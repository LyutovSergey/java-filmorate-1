package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.servise.IdentifyService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

	private final Map<Long, User> users = new HashMap<>();
	private final IdentifyService identifyService = new IdentifyService();

	@Override
	public User get(Long id) {
		User user = users.get(id);
		if (user == null) {
			throw new NotFoundException("Пользователь с id=" + id + " не найден.");
		}
		return user;
	}

	@Override
	public Collection<User> findAll() {
		log.info("Поиск всех пользователей");

		if (users.isEmpty()) {
			throw new NotFoundException("Users not found");
		}
		return users.values();
	}

	@Override
	public User create(User user) {

		log.info("Добавления нового пользователя:");
		userValidate(user);

		log.trace("Проверка логина нового пользователя");
		if (user.getLogin() == null) {
			throw new ValidationException("Логин должен быть указан.");
		}

		log.trace("Проверка даты электронной почты нового пользователя");
		if (user.getEmail() == null) {
			throw new ValidationException("Электронная почта должна быть указана.");
		}

		log.trace("Обработка имени нового пользователя");
		if ((user.getName() == null) || (user.getName().isBlank())) {
			user.setName(user.getLogin());
			log.trace("Пользователю присвоено имя из логина.");
		}

		log.trace("Проверка даты рождения нового пользователя");
		if (user.getBirthday() == null) {
			throw new ValidationException("Дата рождения должна быть указана.");
		}

		log.trace("Сохранение нового пользователя");
		user.setId(identifyService.getNextId(users));
		users.put(user.getId(), user);
		return user;
	}

	@Override
	public User update(User user) {
		log.info("Обновление данных о пользователе:");

		log.trace("Проверка id пользователя");
		if (user.getId() == null) {
			throw new ConditionsNotMetException("Id должен быть указан.");
		} else if (!users.containsKey(user.getId())) {
			throw new NotFoundException("Пользователь с id = " + user.getId() + " не найден.");
		}

		userValidate(user);

		log.trace("Получение текущих данных о пользователе");
		User oldUser = users.get(user.getId());

		log.trace("Обработка логина");
		if (user.getLogin() == null) {
			user.setLogin(oldUser.getLogin());
		} else {
			log.info("Логин пользователя изменен.");
		}

		log.trace("Обработка имени пользователя");
		if (user.getName() == null) {
			user.setName(oldUser.getName());
		} else if ((user.getName().isBlank())) {
			user.setName(user.getLogin());
		} else {
			log.info("Имя пользователя изменено.");
		}

		log.trace("Обработка электронной почты");
		if (user.getEmail() == null) {
			user.setEmail(oldUser.getEmail());
		} else {
			log.info("Электронная почта изменена.");
		}

		log.trace("Обработка даты рождения");
		if (user.getBirthday() == null) {
			user.setBirthday(oldUser.getBirthday());
		} else {
			log.info("Дата рождения изменена.");
		}

		log.trace("Сохранение новых данных пользователя");
		users.put(user.getId(), user);
		return user;
	}

	private void userValidate(User user) {
		log.info("Проверка пользователя:");

		log.trace("Проверка электронной почты");
		if ((user.getEmail() != null) && (user.getEmail().isBlank())) {
			throw new ConditionsNotMetException("Электронная почта не может быть пустой.");
		} else if ((user.getEmail() != null) && !(user.getEmail().contains("@"))) {
			throw new ParameterNotValidException(user.getEmail(),
					"Электронная почта должна содержать символ '@'."
			);
		} else if ((user.getEmail() != null) &&
				users.values().stream().map(User::getEmail).anyMatch(u -> u.equals(user.getEmail()))
		) {
			throw new DuplicatedDataException("Эта электронная почта " + user.getEmail() + " уже используется.");
		}

		log.trace("Проверка логина");
		if ((user.getLogin() != null) && (user.getLogin().isBlank())) {
			throw new ConditionsNotMetException("Логин не может быть пустым.");
		} else if ((user.getLogin() != null) && (user.getLogin().contains(" "))) {
			throw new ParameterNotValidException(user.getLogin(),
					"Логин не должен содержать пробелов."
			);
		}

		log.trace("Проверка даты рождения");
		if ((user.getBirthday() != null) && (user.getBirthday().isAfter(LocalDate.now()))) {
			throw new ParameterNotValidException(user.getBirthday().toString(),
					"Дата рождения не может быть в будущем."
			);
		}
	}
}
