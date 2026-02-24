package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.ReviewDto;
import ru.yandex.practicum.filmorate.dto.request.create.ReviewCreateRequest;
import ru.yandex.practicum.filmorate.dto.request.update.ReviewUpdateRequest;
import ru.yandex.practicum.filmorate.service.ReviewService;

import java.util.Collection;

@RequestMapping("/reviews")
@RestController
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;

	@GetMapping("/{id}")
	public ReviewDto findById(@PathVariable Long id) {
		return reviewService.findReviewById(id);
	}

	@GetMapping
	public Collection<ReviewDto> findByFilmId(
			@RequestParam(required = false) Long filmId,
			@RequestParam(defaultValue = "10") int count
	) {
		return reviewService.findReviewsByFilmId(filmId, count);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
    public ReviewDto create(@RequestBody @Valid ReviewCreateRequest request) {
		return reviewService.createReview(request);
	}

	@PutMapping
	public ReviewDto update(@RequestBody @Valid ReviewUpdateRequest request) {
		return reviewService.updateReview(request);
	}

	@PutMapping("/{id}/like/{userId}")
	public void addLike(@PathVariable Long id, @PathVariable Long userId) {
		reviewService.addReviewLike(id, userId);
	}

	@PutMapping("/{id}/dislike/{userId}")
	public void addDislike(@PathVariable Long id, @PathVariable Long userId) {
		reviewService.addReviewDislike(id, userId);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		reviewService.deleteReview(id);
	}

	@DeleteMapping("/{id}/like/{userId}")
	public void removeLike(@PathVariable Long id, @PathVariable Long userId) {
		reviewService.removeReviewLike(id, userId);
	}

	@DeleteMapping("/{id}/dislike/{userId}")
	public void removeDislike(@PathVariable Long id, @PathVariable Long userId) {
		reviewService.removeReviewDislike(id, userId);
	}
}
