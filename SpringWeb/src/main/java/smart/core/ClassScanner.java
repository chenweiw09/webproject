package smart.core;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * Created by Cruise on 2017/7/2.
 */
public interface ClassScanner {

    List<Class<?>> getClassByPackageName(String packageName);

    /**
     * ��ȡָ��������ָ��ע��������
     */
    List<Class<?>> getAnnotationClassByPackageName(String packageName, Class<? extends Annotation> annotationClass);

    /**
     * ��ȡָ��������ָ�������ӿڵ������
     */
    List<Class<?>> getClassListByPackageName(String packageName, Class<?> superClass);
}
