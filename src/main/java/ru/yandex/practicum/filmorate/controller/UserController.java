package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.servise.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserStorage userStorage;
	private final UserService userService;

	@GetMapping
	public Collection<User> findAll() {
		return userStorage.findAll();
	}

	@GetMapping("/{id}")
	public User get(@PathVariable Long id) {
		return userStorage.get(id);
	}

	@GetMapping("/{id}/friends")
	public Collection<User> getFriends(@PathVariable(name = "id") Long userId) {
		return userService.getListOfFriends(userId);
	}

	@GetMapping("/{id}/friends/common/{otherId}")
	public Collection<User> getMutualFriends(@PathVariable(name = "id") Long userId, @PathVariable Long otherId) {
		return userService.getListOfMutualFriends(userId, otherId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public User create(@RequestBody User user) {
		return userStorage.create(user);
	}

	@PutMapping
	public User update(@RequestBody User user) {
		return userStorage.update(user);
	}

	@PutMapping("/{id}/friends/{friendId}")
	public void addToFriends(@PathVariable(name = "id") Long userId, @PathVariable Long friendId) {
		userService.addToFriends(userId, friendId);
	}

	@DeleteMapping("/{id}/friends/{friendId}")
	public void removeFromFriends(@PathVariable(name = "id") Long userId, @PathVariable Long friendId) {
		userService.removeFromFriends(userId, friendId);
	}
}
