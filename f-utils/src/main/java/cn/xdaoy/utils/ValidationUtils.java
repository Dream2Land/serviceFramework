package cn.xdaoy.utils;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ValidationUtils {
	private static Validator validator;

	static {
		ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
		validator = vf.getValidator();
	}

	/**
	 * @Description: validation function
	 * @param t validation target
	 * @throws
	 */
	public static <T> Set<String> validate(T t) {
		Set<ConstraintViolation<T>> set = validator.validate(t);
		Set<String> result = new HashSet<String>();
		if (set.size() < 1) {
			return result;
		}
		for (ConstraintViolation<T> val : set) {
			result.add(val.getMessage());
		}
		return result;
	}
	
	
}
