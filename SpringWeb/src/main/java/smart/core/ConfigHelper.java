package smart.core;

import org.apache.commons.lang3.StringUtils;
import smart.common.FrameworkConstant;
import smart.util.PropsUtil;

import java.util.Map;
import java.util.Properties;

/**
 * 获取属性文件中的属性值
 *
 * @author huangyong
 * @since 1.0
 */
public class ConfigHelper {

    /**
     * 属性文件对象
     */
    private static final Properties configProps = PropsUtil.loadProps(FrameworkConstant.CONFIG_PROPS);

    /**
     * 获取 String 类型的属性值
     */
    public static String getString(String key) {
        return PropsUtil.getProperties(configProps, key);
    }

    /**
     * 获取 String 类型的属性值（可指定默认值）
     */
    public static String getString(String key, String defaultValue) {
        String value = PropsUtil.getProperties(configProps, key);
        if(StringUtils.isBlank(value))
            return defaultValue;
        return value;
    }

    /**
     * 获取指定前缀的相关属性
     *
     * @since 2.2
     */
    public static Map<String, Object> getMap(String prefix) {
        return PropsUtil.getPrefixMap(configProps, prefix);
    }
}
