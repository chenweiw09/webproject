package com.marker.biz.service;

import com.alibaba.fastjson.JSONObject;
import com.marker.biz.common.Conguration;
import com.marker.biz.utils.DateUtil;
import com.marker.biz.utils.TemplateUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenwei23 on 2016/12/22.
 */
public class GenerateResourceService {
    public void generateResource(){

        TemplateUtil.deleteFile(new File(Conguration.outResourcePath));

        Map<String,Object> root = new HashMap<String, Object>();
        root.put("packagePath", Conguration.packagePath );
        root.put("author", Conguration.author);
        root.put("date", DateUtil.getDate1());
        root.put("APP", "xxxxx");


        TemplateUtil.filePrint(root, Conguration.tempFilePath, "applicationContext.ftl", Conguration.outResourcePath, "applicationContext.xml");

        TemplateUtil.filePrint(root, Conguration.tempFilePath, "dispatcher-servlet.ftl", Conguration.outResourcePath, "dispatcher-servlet.xml");

        TemplateUtil.filePrint(root, Conguration.tempFilePath, "webXml.ftl", Conguration.outResourcePath, "web.xml");

        TemplateUtil.filePrint(root, Conguration.tempFilePath, "mainProperties.ftl", Conguration.outResourcePath, "main.properties");
        TemplateUtil.filePrint(root, Conguration.tempFilePath, "spring-jdbc.ftl", Conguration.outResourcePath, "spring-jdbc.xml");

        TemplateUtil.filePrint(root, Conguration.tempFilePath, "logback.ftl", Conguration.outResourcePath, "logback.xml");
    }
}
