package com.marker.biz.service;

import com.alibaba.fastjson.JSONObject;
import com.marker.biz.common.Conguration;
import com.marker.biz.domain.Attr;
import com.marker.biz.utils.CamelNameUtil;
import com.marker.biz.utils.DateUtil;
import com.marker.biz.utils.TemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenwei23 on 2016/12/22.
 */
public class GenerateControllerService {
    private static final Logger logger = LoggerFactory.getLogger(GenerateDaoService.class);
    /**
     * 生成controller类
     */

    public void generateController(Map<String, List<Attr>> map) {
        logger.info("generateController|param:attrMap=", JSONObject.toJSONString(map));
        if (map == null) {
            logger.info("generateController|param empty error");
            return;
        }

        // 判断
        TemplateUtil.deleteFile(new File(Conguration.outConTrollerPath));

        Map<String, Object> root;
        for (Map.Entry<String, List<Attr>> entry : map.entrySet()) {
            root = new HashMap<String, Object>();
            root.put("packagePath", Conguration.packagePath + ".controller");
            root.put("author", Conguration.author);
            root.put("date", DateUtil.getDate1());
            String className = entry.getKey();
            char[] cs = className.toCharArray();
            cs[0] -= 32;
            String name = String.valueOf(cs);
            System.out.println(name);
            root.put("entityName", name);
            root.put("attrs", entry.getValue());
            root.put("domainPackage", Conguration.packagePath + ".domain." + name);
            root.put("servicePackage", Conguration.packagePath + ".service."+name+"Service");
            root.put("commonPackage", Conguration.packagePath+".common.*");
            logger.info(JSONObject.toJSONString(entry.getValue()));

            TemplateUtil.filePrint(root, Conguration.tempFilePath, "controller.ftl", Conguration.outConTrollerPath, name + "Controller.java");
        }
    }
}
