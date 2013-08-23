package com.amazonia.utils.functional.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Fork {
	/**
	 * How many thread should be forked out
	 * @return
	 */
	int forkNumber() default 1;
}
