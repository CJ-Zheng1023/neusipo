package com.neusoft.neusipo.core.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SendMessage {
    String topic() default "${topic.update.filter.map}";
    String message() default "";
}
