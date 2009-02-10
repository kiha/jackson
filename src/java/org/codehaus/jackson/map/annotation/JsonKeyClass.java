package org.codehaus.jackson.map.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that can be used to mark "setter" methods to indicate the
 * actual type of key Objects for the Map type that is value
 * of the property associated with the method.
 * This is usually done if the declared element type is abstract or
 * too generic; annotation can denote actual concrete type to
 * instantiate when deserializing contents of the container.
 * To define type of the actual container itself, use 
 * {@link JsonClass} instead.
 *<p>
 * Note that the indicated type must be compatible with the declared
 * type; that is, it has to be a sub-type or implementation of
 * the declared type. This is usually the case; and if it wasn't
 * then the call to associated "setter" method would fail with
 * a type-mismatch exception.
 *<p>
 * Note: while any class can be indicated as the Key class, there
 * must be a registered Key Deserializer for the type.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonKeyClass
{
    /**
     * Class that is the expected concrete value type of the container
     * (which is value of the property associated
     * with the annotated method). Will be used by deserializer to
     * instantiate the type, using
     *<p>
     * Note: if a non-property method is annotated with this annotation,
     * deserializer will throw an exception to denote invalid annotation.
     */
    public Class<?> value();
}
