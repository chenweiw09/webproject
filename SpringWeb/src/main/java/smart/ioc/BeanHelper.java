package smart.ioc;

import smart.core.ClassHelper;
import smart.core.exception.InitializationException;
import smart.ioc.annotation.Bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Cruise on 2017/7/2.
 */
public class BeanHelper {
    private static final Map<Class<?>, Object> beanMap = new HashMap<Class<?>, Object>();

    static {
        List<Class<?>> classList = ClassHelper.getClassList();
        try {
            for(Class<?> cls : classList){
                // ������� Bean/Service/Action/Aspect ע�����
                if(cls.isAnnotationPresent(Bean.class)){
    //                    || cls.isAnnotationPresent(Bean.class)
    //                    || cls.isAnnotationPresent(Bean.class)
    //                    ||cls.isAnnotationPresent(Bean.class)){

                    Object instance = cls.newInstance();
                    beanMap.put(cls,instance);
                }
            }
        } catch (Exception e) {
            throw new InitializationException("��ʼ�� BeanHelper ����", e);
        }
    }

    public static Map<Class<?>, Object> getBeanMap(){
        return beanMap;
    }

    public static <T> T getBean(Class<T> cls){
        if(!beanMap.containsKey(cls)){
            throw new RuntimeException("no bean instance has loaded");
        }
        return (T) beanMap.get(cls);
    }

    public static void  setBean(Class<?> cls, Object instance){
        beanMap.put(cls,instance);
    }
}
