package ru.yandex.practicum.filmorate.dto.request.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewCreateRequest {
    @NotBlank(message = "Контент отзыва не может быть пустым.")
    private String content;

    @NotNull(message = "Тип отзыва должен быть указан.")
    private Boolean isPositive;

    @NotNull(message = "userId должен быть указан.")
    private Long userId;

    @NotNull(message = "filmId должен быть указан.")
    private Long filmId;
}
