package ru.yandex.practicum.filmorate.dto.adapters;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MinDateValidator implements ConstraintValidator<MinDate, LocalDate> {
	private LocalDate minDate;

	@Override
	public void initialize(MinDate constraintAnnotation) {
		this.minDate = LocalDate.parse(constraintAnnotation.minDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

	@Override
	public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		return !value.isBefore(minDate);
	}
}

