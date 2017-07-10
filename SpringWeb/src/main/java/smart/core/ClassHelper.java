package smart.core;

import smart.common.FrameworkConstant;
import smart.common.InstanceFactory;
import smart.util.PropsUtil;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Properties;

/**
 * Created by Cruise on 2017/7/2.
 * ����������ȡ�����
 */
public class ClassHelper {
    private static Properties props;
    static {
        props = PropsUtil.loadProps(FrameworkConstant.CONFIG_PROPS);
    }
    private static final String basePackage = props.getProperty(FrameworkConstant.BASEPACKAGE);


    private static final ClassScanner classScanner = InstanceFactory.getClassScanner();

    public static List<Class<?>> getClassList() {
        System.out.println(basePackage+"---------------");
        return classScanner.getClassByPackageName(basePackage);
    }

    /**
     * ��ȡ����������ָ�������ӿڵ������
     */
    public static List<Class<?>> getClassListBySuper(Class<?> superClass){
        return classScanner.getClassListByPackageName(basePackage, superClass);
    }

    /**
     * ��ȡ����������ָ��ע��������
     */
    public static List<Class<?>> getClassListByAnnotation(Class<? extends Annotation> annotationClass) {
        return classScanner.getAnnotationClassByPackageName(basePackage, annotationClass);
    }

}
