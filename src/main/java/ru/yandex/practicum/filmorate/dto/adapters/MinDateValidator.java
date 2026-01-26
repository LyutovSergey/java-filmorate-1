package ru.yandex.practicum.filmorate.dto.adapters;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Класс MinDateValidator реализует проверку минимального значения даты.
 * Он используется для валидации аннотации @MinDate и проверяет, что дата не меньше заданной минимальной даты.
 */
public class MinDateValidator implements ConstraintValidator<MinDate, LocalDate> {
	/**
	 * Минимальное значение даты, с которым сравнивается проверяемая дата.
	 */
	private LocalDate minDate;

	/**
	 * Инициализирует валидатор минимальным значением даты, заданным в аннотации.
	 * @param constraintAnnotation аннотация @MinDate
	 */
	@Override
	public void initialize(MinDate constraintAnnotation) {
		this.minDate = LocalDate.parse(constraintAnnotation.minDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

	/**
	 * Проверяет, что указанная дата не меньше минимальной даты.
	 * Если проверяемая дата равна null, считается, что проверка прошла успешно.
	 * @param value проверяемая дата
	 * @param context контекст валидации
	 * @return true, если дата не меньше минимальной или равна null, иначе false
	 */
	@Override
	public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		return !value.isBefore(minDate);
	}
}

