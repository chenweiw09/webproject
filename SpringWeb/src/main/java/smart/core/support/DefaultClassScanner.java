package smart.core.support;

import smart.core.ClassScanner;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * Created by Cruise on 2017/7/2.
 * Ĭ�ϵ���ɨ�蹤��
 */
public class DefaultClassScanner implements ClassScanner {
    public List<Class<?>> getClassByPackageName(String packageName) {
        return new ClassTemplate(packageName){
            @Override
            public boolean checkAddClass(Class<?> cls) {
                String className = cls.getName();
                String pkgName = className.substring(0, className.lastIndexOf("."));
                return pkgName.startsWith(packageName);
            }
        }.getClassList();
    }

    public List<Class<?>> getAnnotationClassByPackageName(String packageName, Class<? extends Annotation> annotationClass) {
        return new AnnotationClassTemplate(packageName, annotationClass) {
            @Override
            public boolean checkAddClass(Class<?> cls) {
                return cls.isAnnotationPresent(annotationClass);
            }
        }.getClassList();
    }

    /**
     * ��ȡsupserClass�µ��������
     * @param packageName
     * @param superClass
     * @return
     */
    public List<Class<?>> getClassListByPackageName(String packageName, Class<?> superClass) {
        return new SuperClassTemplate(packageName, superClass){
            @Override
            public boolean checkAddClass(Class<?> cls) {
                return superClass.isAssignableFrom(cls) && !superClass.equals(cls);
            }
        }.getClassList();
    }
}
