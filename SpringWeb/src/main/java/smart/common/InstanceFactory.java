package smart.common;

import org.apache.commons.lang3.StringUtils;
import smart.core.ClassScanner;
import smart.core.ConfigHelper;
import smart.core.support.DefaultClassScanner;
import smart.util.ClassUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Cruise on 2017/7/2.
 */
public class InstanceFactory {

    /**
     * 用于缓存对应的实例
     */
    private static final Map<String, Object> cache = new ConcurrentHashMap<String, Object>();

    /**
     * ClassScanner
     */
    private static final String CLASS_SCANNER = "smart.framework.custom.class_scanner";

    /**
     * 获取 ClassScanner
     */
    public static ClassScanner getClassScanner() {
        return getInstance(CLASS_SCANNER, DefaultClassScanner.class);
    }

    public static<T> T getInstance(String cacheKey, Class<T> defaultImplClass) {
        if (cache.containsKey(cacheKey)) {
            return (T) cache.get(cacheKey);
        }

        String implClassName = ConfigHelper.getString(cacheKey);
        if(StringUtils.isBlank(implClassName)){
            implClassName = defaultImplClass.getName();
        }

        T instance = null;
        try {
            instance = (T) ClassUtil.loadClass(implClassName).newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 若该实例不为空，则将其放入缓存
        if (instance != null) {
            cache.put(cacheKey, instance);
        }
        // 返回该实例
        return instance;
    }
}
