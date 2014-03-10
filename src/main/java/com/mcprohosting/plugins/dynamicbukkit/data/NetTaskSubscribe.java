package com.mcprohosting.plugins.dynamicbukkit.data;

import java.lang.String;import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NetTaskSubscribe {
    public String name();
    public String[] args();
}
