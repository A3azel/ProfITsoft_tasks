package profITsoft.lectures5_6.task2.annotations;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Property {
    String name() default "";
    String format() default "";
}
