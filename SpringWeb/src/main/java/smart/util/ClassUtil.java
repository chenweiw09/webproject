package smart.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

/**
 * Created by Cruise on 2017/6/29.
 */
public final class ClassUtil {
    private static final Logger logger = LoggerFactory.getLogger(ClassUtil.class);

    public static ClassLoader getClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * get class path
     */

    public static String getClassPath(){
        String classPath = "";
        URL resource = getClassLoader().getResource("");
        if(resource != null){
            classPath = resource.getPath();
        }

        return classPath;
    }

    /**
     * load class
     */
    public static Class<?> loadClass(String className, boolean isInitialized){
        Class<?> cls;
        try {
            cls = Class.forName(className, isInitialized, getClassLoader());

        } catch (ClassNotFoundException e) {
            logger.error("class load exception",e);
            throw new RuntimeException(e);
        }
        return cls;
    }

    public static Class<?> loadClass(String className){
        return loadClass(className,true);
    }

    public static boolean isInt(Class<?> type){
        return int.class.equals(type)|| Integer.class.equals(type);
    }

    public static boolean isLong(Class<?> type){
        return long.class.equals(type) || Long.class.equals(type);
    }

    public static boolean isDouble(Class<?> type){
        return double.class.equals(type) || Double.class.equals(type);
    }

    public static boolean isString(Class<?> type) {
        return type.equals(String.class);
    }
}
