package com.neusoft.neusipo.core.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Execution {
    String logCode() default "";
    String errorCode() default "";
}
