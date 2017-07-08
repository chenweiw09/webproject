package smart.aop.annotation;

import java.lang.annotation.*;

/**
 * Created by Cruise on 2017/7/6.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Aspect {
    String pkg() default "";

    String cls() default "";

    Class<? extends Annotation> annotation() default Aspect.class;
}
