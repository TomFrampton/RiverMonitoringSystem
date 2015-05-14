package u1171639.shared.main.java.utils;

import java.util.List;

import u1171639.shared.main.java.exception.ValidationException;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;

public class ValidationUtils {
	public static void validate(Object o) throws ValidationException {
		Validator validator = new Validator();
		List<ConstraintViolation> violations = validator.validate(o);
		
		if(violations.size() > 0) {
			throw new ValidationException(violations.size() + " validation errors.", violations);
		}
	}
}
