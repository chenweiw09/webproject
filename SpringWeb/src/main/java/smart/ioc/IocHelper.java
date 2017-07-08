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
                // 获取 Bean 类与 Bean 实例
                Class<?> beanClass = entry.getKey();
                Object beanInstance = entry.getValue();

                // 获取 Bean 类中所有的字段（不包括父类中的方法）
                Field[] fields = beanInstance.getClass().getDeclaredFields();
                if(ArrayUtils.isNotEmpty(fields)){
                    for(Field beanField:fields){
                        if(beanField.isAnnotationPresent(Inject.class)){
                            // 获取 Bean 字段对应的接口
                            Class<?> interfaceClass = beanField.getType();
                            // 获取 Bean 字段对应的实现类
                            Class<?> implementClass = findImplementClass(interfaceClass);
                            if(implementClass != null){
                                // 从 Bean Map 中获取该实现类对应的实现类实例
                                Object implementInstance = beanMap.get(implementClass);
                                // 设置该 Bean 字段的值
                                if(implementInstance != null){
                                    beanField.setAccessible(true);
                                    beanField.set(beanInstance,implementInstance);
                                }else {
                                    throw new InitializationException("依赖注入失败！类名：" + beanClass.getSimpleName() + "，字段名：" + interfaceClass.getSimpleName());
                                }
                            }
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new InitializationException("初始化 IocHelper 出错！", e);
        }
    }

    public static Class<?> findImplementClass(Class<?> interfaceClass){
        Class<?> implementClass = interfaceClass;

        //如果接口上表明了Impl注解，就直接取此类
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
