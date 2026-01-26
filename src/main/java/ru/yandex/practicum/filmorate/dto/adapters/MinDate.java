package ru.yandex.practicum.filmorate.dto.adapters;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * Аннотация MinDate используется для проверки того, что дата не может быть раньше указанной минимальной даты.
 * Она применяется к полям и параметрам и валидируется с помощью MinDateValidator.
 */
@Documented
@Constraint(validatedBy = MinDateValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface MinDate {
	/**
	 * Сообщение, которое будет отображаться в случае, если проверка не пройдена.
	 * @return сообщение по умолчанию: "Дата не может быть раньше {minDate}"
	 */
	String message() default "Дата не может быть раньше {minDate}";

	/**
	 * Группы, к которым относится валидация.
	 * @return массив классов групп по умолчанию
	 */
	Class<?>[] groups() default {};

	/**
	 * Пэйлоады, связанные с валидацией.
	 * @return массив классов пейлоадов по умолчанию
	 */
	Class<? extends Payload>[] payload() default {};

	/**
	 * Указывает минимальную дату, с которой будет сравниваться проверяемая дата.
	 * Формат даты должен соответствовать шаблону "yyyy-MM-dd".
	 * @return минимальная дата для проверки
	 */
	String minDate();
}
