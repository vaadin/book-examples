package com.vaadin.book.examples.lib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description for an example.
 * 
 * The annotation can be used for example methods in example bundles currently.
 * It is also allowed for UI classes embedded with {@link EmboExample}, but
 * reading those annotations is not yet supported.
 * 
 * @author magi
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Description {
    public String title() default "";
    public String value() default "";
}
