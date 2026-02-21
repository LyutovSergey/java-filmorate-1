package ru.yandex.practicum.filmorate.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder(toBuilder = true)
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Jacksonized
public class Director {
	Integer id;
	String name;
}
