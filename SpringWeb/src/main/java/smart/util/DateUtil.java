package smart.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ���ڲ���������
 * @since 1.0
 */
public class DateUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    private static final SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    /**
     * ��ʽ��������ʱ��
     */
    public static String formatDatetime(long timestamp) {
        return datetimeFormat.format(new Date(timestamp));
    }

    /**
     * ��ʽ������
     */
    public static String formatDate(long timestamp) {
        return dateFormat.format(new Date(timestamp));
    }

    /**
     * ��ʽ��ʱ��
     */
    public static String formatTime(long timestamp) {
        return timeFormat.format(new Date(timestamp));
    }

    /**
     * ��ȡ��ǰ������ʱ��
     */
    public static String getCurrentDatetime() {
        return datetimeFormat.format(new Date());
    }

    /**
     * ��ȡ��ǰ����
     */
    public static String getCurrentDate() {
        return dateFormat.format(new Date());
    }

    /**
     * ��ȡ��ǰʱ��
     */
    public static String getCurrentTime() {
        return timeFormat.format(new Date());
    }

    /**
     * ����������ʱ��
     */
    public static Date parseDatetime(String str) {
        Date date = null;
        try {
            date = datetimeFormat.parse(str);
        } catch (ParseException e) {
            logger.error("���������ַ���������ʽ��yyyy-MM-dd HH:mm:ss", e);
        }
        return date;
    }

    /**
     * ��������
     */
    public static Date parseDate(String str) {
        Date date = null;
        try {
            date = dateFormat.parse(str);
        } catch (ParseException e) {
            logger.error("���������ַ���������ʽ��yyyy-MM-dd", e);
        }
        return date;
    }

    /**
     * ����ʱ��
     */
    public static Date parseTime(String str) {
        Date date = null;
        try {
            date = timeFormat.parse(str);
        } catch (ParseException e) {
            logger.error("���������ַ���������ʽ��HH:mm:ss", e);
        }
        return date;
    }
}
