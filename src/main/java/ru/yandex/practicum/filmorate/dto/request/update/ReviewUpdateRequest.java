package ru.yandex.practicum.filmorate.dto.request.update;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewUpdateRequest {
    @NotNull(message = "reviewId должен быть указан.")
    private Long reviewId;

    @NotNull(message = "Контент отзыва не может быть пустым.")
    private String content;

    @NotNull(message = "Тип отзыва должен быть указан.")
    private Boolean isPositive;
}
