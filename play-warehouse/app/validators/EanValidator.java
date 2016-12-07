package validators;

import play.data.validation.Constraints;
import play.libs.F;
import validators.annotations.EAN;

import javax.validation.ConstraintValidator;

public class EanValidator extends Constraints.Validator<String> implements ConstraintValidator<EAN, String> {
    private static final String MESSAGE = "error.invalid.ean";

    @Override
    public void initialize(EAN constraintAnnotation) { }

    @Override
    public boolean isValid(String value) {
        return value.indexOf('-') > 0;
    }

    @Override
    public F.Tuple<String, Object[]> getErrorMessageKey() {
        return F.Tuple(MESSAGE, new Object[]{ });
    }
}
