package com.marker.biz.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chenwei23 on 2016/12/22.
 */
public class DateUtil {



    public static String getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    public static String getDate1(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(new Date());
    }


}
