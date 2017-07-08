package smart.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by Cruise on 2017/6/29.
 */
public final class PropsUtil {
    private static final Logger logger = LoggerFactory.getLogger(PropsUtil.class);

    public static Properties loadProps(String path) {
        Properties props = new Properties();
        InputStream input = null;

        if (StringUtils.isBlank(path)) {
            throw new IllegalArgumentException();
        }
        String suffix = ".properties";
        if (path.lastIndexOf(suffix) != 1) {
            path += suffix;
        }
        try {
            input = ClassUtil.getClassLoader().getResourceAsStream(path);
            if (input != null) {
                props.load(input);
            }
        } catch (IOException e) {
            logger.error("load properties wrong",e);
            throw new RuntimeException(e);
        }finally {
            if(input != null){
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return props;
    }

    /**
     * get the property by key
     * @param props
     * @param key
     * @return
     */
    public static String getProperties(Properties props, String key){
        String value = "";
        if(props.contains(key)){
            value = props.getProperty(key);
        }
        return value;
    }

    public static String getProperties(Properties props, String key, String defaultValue){
        String value = defaultValue;
        if(props.contains(key)){
            value = props.getProperty(key);
        }
        return value;
    }

    public static Map<String, String> getPropertiesToMap(Properties props){
        Map<String, String> map = new HashMap<String, String>();
        for(String key : props.stringPropertyNames()){
            map.put(key, props.getProperty(key));
        }
        return map;
    }

    public static Map<String, String> getPropertiesToMap(String filePath){
        Properties props = loadProps(filePath);
        return props == null ? null:getPropertiesToMap(props);
    }

    public static int getIntProperty(Properties props, String key){
        int retValue = 0;
        String value = getProperties(props, key);
        if(StringUtils.isNoneBlank(value)){
            retValue = Integer.parseInt(value);
        }

        return retValue;
    }

    public static boolean getBooleanProperty(Properties props, String key){
        boolean retValue = false;
        String value = getProperties(props, key);
        if(StringUtils.isNoneBlank(value)){
            retValue = Boolean.parseBoolean(value);
        }

        return retValue;
    }

    public static Map<String, Object> getPrefixMap(Properties props, String prefix){
        Map<String, Object> kvMap = new LinkedHashMap<String, Object>();
        Set<String> keySet = props.stringPropertyNames();
        for(String key : keySet){
            if(key.startsWith(prefix)){
                kvMap.put(key, props.getProperty(key));
            }
        }
        return kvMap;
    }
}
