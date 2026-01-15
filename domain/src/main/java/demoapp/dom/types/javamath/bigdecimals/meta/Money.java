package demoapp.dom.types.javamath.bigdecimals.meta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.constraints.Digits;

@Digits(integer = 12, fraction = 2)
// @javax.persistence.Column(precision = 14, scale = 2) // JPA doesn't support meta-annotations
@javax.jdo.annotations.Column(scale = 2)                // JDO doesn't require length to be specified
@Inherited
@Target({
        ElementType.METHOD, ElementType.FIELD,                      // <.>
        ElementType.PARAMETER,                                      // <.>
})
@Retention(RetentionPolicy.RUNTIME)
public @interface Money {
}
