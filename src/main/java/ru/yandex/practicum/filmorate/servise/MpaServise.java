package ru.yandex.practicum.filmorate.servise;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.database.MpaDbStorage;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class MpaServise {

	private final MpaDbStorage mpaStorage;

	public Mpa getMpaById(long mpaId) {
		log.info("Получение рейтинга MPAA с id={}", mpaId);
		return mpaStorage.getMpaById(mpaId)
				.orElseThrow(() -> new NotFoundException(""));
	}

	public Collection<Mpa> getAllMpa() {
		log.info("Получение всех рейтингов MPAA");
		return mpaStorage.getAllMpa();
	}
}
