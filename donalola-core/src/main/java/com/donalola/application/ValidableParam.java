package com.donalola.application;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidableParam {
}
