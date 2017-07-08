package smart.core;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * Created by Cruise on 2017/7/2.
 */
public interface ClassScanner {

    List<Class<?>> getClassByPackageName(String packageName);

    /**
     * 获取指定包名中指定注解的相关类
     */
    List<Class<?>> getAnnotationClassByPackageName(String packageName, Class<? extends Annotation> annotationClass);

    /**
     * 获取指定包名中指定父类或接口的相关类
     */
    List<Class<?>> getClassListByPackageName(String packageName, Class<?> superClass);
}
