package com.example.demo.shared.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD}) // can be applied to method
@Retention(RetentionPolicy.RUNTIME) // available at runtime
public @interface TrackExecutionTime {}
