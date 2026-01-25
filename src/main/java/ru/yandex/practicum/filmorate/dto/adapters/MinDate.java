package ru.yandex.practicum.filmorate.dto.adapters;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MinDateValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface MinDate {
	String message() default "Дата не может быть раньше {minDate}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	String minDate();
}

