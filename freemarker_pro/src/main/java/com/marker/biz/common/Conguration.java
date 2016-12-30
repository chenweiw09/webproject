package com.marker.biz.common;

/**
 * Created by chenwei23 on 2016/12/14.
 */
public class Conguration {
    //  模板文件位置
    public static String tempFilePath = "/tempfile/";
    //工程包的名称
    public static String packagePath = "com.my.freemarker.biz";
    //工程文件目录
    public static String outFilePath = "project/"+packagePath.replace(".","/")+"/";
    //jsp文件目录
    public static String outJspPath = outFilePath+"jsp";
    //domain文件目录
    public static String outDomainPath = outFilePath+"domain";
    //service文件目录
    public static String outServicePath = outFilePath+"service";
    //serviceImpl文件目录
    public static String outServiceImplPath = outFilePath+"service/impl";
    //dao文件目录
    public static String outDaoPath = outFilePath+"dao";
    //Controller文件目录
    public static String outConTrollerPath = outFilePath+"controller";
    //util文件目录
    public static String outUtilPath = outFilePath+"utils";

    public static String outFileName = "test.html";

    public static String author = "chenwei";


}
