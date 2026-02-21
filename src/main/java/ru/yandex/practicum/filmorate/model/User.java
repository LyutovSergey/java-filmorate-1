package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder(toBuilder = true)
@EqualsAndHashCode(of = "id")
@Jacksonized
public class User {

	private Long id;
	private String name;
	private String login;
	private String email;
	private LocalDate birthday;

	@Builder.Default
	private Set<Long> friendsIds = new HashSet<>();

	public void addFriendId(Long id) {
		friendsIds.add(id);
	}

	public void removeFriends(Long id) {
		friendsIds.remove(id);
	}
}
