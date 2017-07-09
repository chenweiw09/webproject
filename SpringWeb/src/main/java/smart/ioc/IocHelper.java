package smart.ioc;

import org.apache.commons.lang3.ArrayUtils;
import smart.core.ClassHelper;
import smart.core.exception.InitializationException;
import smart.ioc.annotation.Impl;
import smart.ioc.annotation.Inject;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Created by Cruise on 2017/7/2.
 * initialize the ioc container
 */
public class IocHelper {

    static{
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        try {
            for(Map.Entry<Class<?>, Object> entry : beanMap.entrySet()){
                // ��ȡ Bean ���� Bean ʵ��
                Class<?> beanClass = entry.getKey();
                Object beanInstance = entry.getValue();

                // ��ȡ Bean �������е��ֶΣ������������еķ�����
                Field[] fields = beanInstance.getClass().getDeclaredFields();
                if(ArrayUtils.isNotEmpty(fields)){
                    for(Field beanField:fields){
                        if(beanField.isAnnotationPresent(Inject.class)){
                            // ��ȡ Bean �ֶζ�Ӧ�Ľӿ�
                            Class<?> interfaceClass = beanField.getType();
                            // ��ȡ Bean �ֶζ�Ӧ��ʵ����
                            Class<?> implementClass = findImplementClass(interfaceClass);
                            if(implementClass != null){
                                // �� Bean Map �л�ȡ��ʵ�����Ӧ��ʵ����ʵ��
                                Object implementInstance = beanMap.get(implementClass);
                                // ���ø� Bean �ֶε�ֵ
                                if(implementInstance != null){
                                    beanField.setAccessible(true);
                                    beanField.set(beanInstance,implementInstance);
                                }else {
                                    throw new InitializationException("����ע��ʧ�ܣ�������" + beanClass.getSimpleName() + "���ֶ�����" + interfaceClass.getSimpleName());
                                }
                            }
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new InitializationException("��ʼ�� IocHelper ����", e);
        }
    }

    public static Class<?> findImplementClass(Class<?> interfaceClass){
        Class<?> implementClass = interfaceClass;

        //����ӿ��ϱ�����Implע�⣬��ֱ��ȡ����
        if(interfaceClass.isAnnotationPresent(Impl.class)){
            implementClass = interfaceClass.getAnnotation(Impl.class).vaule();
        }else{
            List<Class<?>> cls = ClassHelper.getClassListBySuper(interfaceClass);
            if(cls != null && cls.size() > 0){
                implementClass = cls.get(0);
            }
        }

        return implementClass;
    }
}
