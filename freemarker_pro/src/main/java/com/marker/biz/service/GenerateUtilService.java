package com.marker.biz.service;

import com.alibaba.fastjson.JSONObject;
import com.marker.biz.common.Conguration;
import com.marker.biz.utils.DateUtil;
import com.marker.biz.utils.TemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenwei23 on 2016/12/20.
 */
public class GenerateUtilService {
    private static Logger logger = LoggerFactory.getLogger(GenerateUtilService.class);

    public void generateUtil(){
        logger.info("GenerateModelSqlUtilService|生成格式化数据库查询语句的工具类");
        // 判断
        TemplateUtil.deleteFile(new File(Conguration.outUtilPath));
        Map<String,Object> root = new HashMap<String, Object>();
        root.put("packageName", Conguration.packagePath + ".utils");
        root.put("author", Conguration.author);
        root.put("date", DateUtil.getDate1());

        logger.info(JSONObject.toJSONString(root));

        //generate modelsqlutil
        TemplateUtil.filePrint(root, Conguration.tempFilePath, "modelSqlExtUtil.ftl", Conguration.outUtilPath, "ModelSqlExtUtil.java");
        //generate cameutil
        TemplateUtil.filePrint(root, Conguration.tempFilePath, "camelNameUtil.ftl", Conguration.outUtilPath, "CamelNameUtil.java");
    }
}
