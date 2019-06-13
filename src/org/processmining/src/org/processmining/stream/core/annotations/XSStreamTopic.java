package org.processmining.stream.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.processmining.stream.core.interfaces.XSDataPacket;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface XSStreamTopic {
	Class<?> value() default XSDataPacket.class;
}
