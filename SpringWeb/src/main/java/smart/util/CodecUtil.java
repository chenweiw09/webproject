package smart.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smart.common.FrameworkConstant;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * Created by Cruise on 2017/7/1.
 */
public final class CodecUtil {
    private static final Logger logger = LoggerFactory.getLogger(CodecUtil.class);

    public static String encodeUrl(String str){
        String target;
        try {
            target = URLEncoder.encode(str, FrameworkConstant.UTF_8);

        } catch (UnsupportedEncodingException e) {
            logger.error("encode url exception|",e);
            throw new RuntimeException(e);
        }
        return target;
    }

    public static String decodeURL(String str){
        String destStr;
        try {
            destStr = URLDecoder.decode(str, FrameworkConstant.UTF_8);
        } catch (UnsupportedEncodingException e) {
            logger.error("decore url exception|",e);
            throw new RuntimeException(e);
        }
        return destStr;
    }

    public static String EncodeBase64(String str){
        String target;
        try {
            target = new String(Base64.encodeBase64(str.getBytes(FrameworkConstant.UTF_8)));
        } catch ( UnsupportedEncodingException e) {
            logger.error("encode url exception|",e);
            throw new RuntimeException(e);
        }
        return target;
    }

    public static String decodeBase64(String str){
        String target;
        try {
            target = new String(Base64.decodeBase64(str.getBytes(FrameworkConstant.UTF_8)));
        } catch ( UnsupportedEncodingException e) {
            logger.error("decode url exception|",e);
            throw new RuntimeException(e);
        }
        return target;
    }

    public static String encryptMD5(String str){
        return DigestUtils.md2Hex(str);
    }

    public static String encryptSHA(String str){
        return DigestUtils.sha1Hex(str);
    }

    public static String createUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
