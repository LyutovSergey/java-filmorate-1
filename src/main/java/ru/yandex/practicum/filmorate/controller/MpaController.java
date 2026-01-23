package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.servise.MpaServise;

import java.util.Collection;

@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class MpaController {
	private final MpaServise mpaService;

	@GetMapping("/{mpaId}")
	public Mpa getMpaById(@PathVariable long mpaId) {
		return mpaService.getMpaById(mpaId);
	}

	@GetMapping
	public Collection<Mpa> getAllMpa() {
		return mpaService.getAllMpa();
	}
}
