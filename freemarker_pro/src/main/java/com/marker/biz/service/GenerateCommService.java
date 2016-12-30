package com.marker.biz.service;

import com.marker.biz.common.Conguration;
import com.marker.biz.utils.DateUtil;
import com.marker.biz.utils.TemplateUtil;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenwei23 on 2016/12/21.
 */
public class GenerateCommService {

    /**
     * 生成pom文件
     */
    public void generatePom(){

        // 判断
        TemplateUtil.deleteFile(new File(Conguration.projectName));

        Map<String,Object> root = new HashMap<String, Object>();
        root.put("groupId", "xxxxxx");
        root.put("projectId","ddddd");
        root.put("author", Conguration.author);
        root.put("date", DateUtil.getDate1());

        TemplateUtil.filePrint(root, Conguration.tempFilePath, "pom.ftl", Conguration.projectName, "pom.xml");
    }

    /**
     * 生成common文件
     */
    public void generateComm(){
        // 判断
        TemplateUtil.deleteFile(new File(Conguration.outCommonPath));

        Map<String,Object> root = new HashMap<String, Object>();
        root.put("packageName", Conguration.packagePath+".common");
        root.put("author", Conguration.author);
        root.put("date", DateUtil.getDate1());

        TemplateUtil.filePrint(root, Conguration.tempFilePath, "pageSet.ftl", Conguration.outCommonPath, "PageSet.java");
        TemplateUtil.filePrint(root, Conguration.tempFilePath, "commResult.ftl", Conguration.outCommonPath, "CommResult.java");
    }



}
