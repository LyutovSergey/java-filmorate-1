package ru.yandex.practicum.filmorate.servise;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.DirectorStorage;
import ru.yandex.practicum.filmorate.dto.DirectorDto;
import ru.yandex.practicum.filmorate.dto.request.create.DirectorCreateRequest;
import ru.yandex.practicum.filmorate.dto.request.update.DirectorUpdateRequest;
import ru.yandex.practicum.filmorate.exception.MethodNotImplementedException;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectorService {
	private final DirectorStorage directorStorage;

	public DirectorDto getDirectorDtoById(long directorId) {
		log.info("Получение режиссера с id={}", directorId);
		// todo
		throw new MethodNotImplementedException();
	}

	public Collection<DirectorDto> getAllDirectorDtos() {
		log.info("Получение всех режиссеров");
		// todo
		throw new MethodNotImplementedException();
	}

	public DirectorDto create(DirectorCreateRequest request) {
		log.info("Добавление нового режиссера name={}.", request.getName());
		// todo
		throw new MethodNotImplementedException();
	}

	public DirectorDto update(DirectorUpdateRequest request) {
		log.info("Обновление данных режиссера с id={}.", request.getId());
		// todo
		throw new MethodNotImplementedException();
	}

	public void delete(Long directorId) {
		log.info("Удаление режиссера с id={}", directorId);
		// todo
		throw new MethodNotImplementedException();
	}
}
