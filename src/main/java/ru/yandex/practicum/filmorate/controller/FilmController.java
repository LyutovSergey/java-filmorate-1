package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.servise.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

    private final FilmStorage filmStorage;
    private final FilmService filmService;

    @GetMapping
    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    @GetMapping("/{id}")
    public Film get(@PathVariable Long id) {
        return filmStorage.get(id);
    }

    @GetMapping("/popular")
    public Collection<Film> getTop(@RequestParam(defaultValue = "10") String count) {
        return filmService.getTopFilms(Integer.parseInt(count));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film create(@RequestBody Film film) {
        return filmStorage.create(film);
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        return filmStorage.update(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void setLike(@PathVariable(name = "id") Long filmId, @PathVariable Long userId) {
        filmService.setLike(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable(name = "id") Long filmId, @PathVariable Long userId) {
        filmService.unsetLike(filmId, userId);
    }
}
