package org.processmining.stream.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.processmining.stream.core.interfaces.XSSignature;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface XSStreamSignature {
	Class<?> value() default XSSignature.class;
}
