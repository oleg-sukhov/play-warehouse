package validators.annotations;

import validators.EanValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = EanValidator.class)
@Target(FIELD)
@Retention(RUNTIME)

public @interface EAN {

    String message() default "error.invalid.ean";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
