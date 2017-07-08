package smart;

import org.apache.commons.lang3.StringUtils;
import smart.core.ClassScanner;
import smart.core.ConfigHelper;
import smart.core.support.DefaultClassScanner;
import smart.util.ClassUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by chenwei23 on 2017/7/8.
 *
 */
public class InstanceFactory {

    private static final Map<String, Object> cache = new ConcurrentHashMap<String, Object>();

    private static final String CLASS_SCANNER = "smart.framework.class_scanner";

    public static ClassScanner getClassScanner(){
        return getInstance(CLASS_SCANNER, DefaultClassScanner.class);
    }

    public static <T> T getInstance(String cacheKey, Class<T> defaultImplClass){
        if(cache.containsKey(cacheKey)){
            return (T) cache.get(cacheKey);
        }

        String implClassName = ConfigHelper.getString(cacheKey);

        if(StringUtils.isNotEmpty(implClassName)){
            implClassName = defaultImplClass.getName();
        }

        T instance = newInstance(implClassName);
        if(instance != null){
            cache.put(cacheKey,instance);
        }

        return instance;
    }

    public static <T> T newInstance(String className){
        T instance;

        try {
            Class<?> commandClass = ClassUtil.loadClass(className);
            instance = (T) commandClass.newInstance();

        } catch (Exception e) {
           throw new RuntimeException(e);
        }
        return instance;
    }
}
