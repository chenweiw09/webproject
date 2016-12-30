package com.marker.biz.service;

import com.alibaba.fastjson.JSONObject;
import com.marker.biz.common.Conguration;
import com.marker.biz.domain.Attr;
import com.marker.biz.utils.TemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenwei23 on 2016/12/20.
 */
public class generateDaoService {
    private static final Logger logger = LoggerFactory.getLogger(generateDaoService.class);

    /**
     * 生成baseDao文件
     */
    public void generateBaseDao(){
        // 判断
        TemplateUtil.deleteFile(new File(Conguration.outDaoPath));

        Map<String,Object> root = new HashMap<String, Object>();
        root.put("packageName", Conguration.outDaoPath);
        root.put("author", Conguration.author);

        TemplateUtil.filePrint(root, Conguration.tempFilePath, "baseDao.ftl", Conguration.outDaoPath, "BaseDao.java");
    }

    /**
     * 生成dao文件
     * @param map
     */
    public void generateDao(Map<String, List<Attr>> map) {
        logger.info("generateDao|param:attrMap=", JSONObject.toJSONString(map));
        if (map == null) {
            logger.info("generateDao|param empty error");
            return;
        }

        // 判断
        TemplateUtil.deleteFile(new File(Conguration.outDaoPath));

        Map<String, Object> root;
        for (Map.Entry<String, List<Attr>> entry : map.entrySet()) {
            root = new HashMap<String, Object>();
            root.put("packagePath", Conguration.packagePath + ".dao");
            root.put("author", Conguration.author);

            String className = entry.getKey();
            char[] cs = className.toCharArray();
            cs[0] -= 32;
            String name = String.valueOf(cs);
            System.out.println(name);
            root.put("entityName", name);
            root.put("attrs", entry.getValue());
            root.put("domainPackage", Conguration.packagePath + ".domain." + name);

            logger.info(JSONObject.toJSONString(entry.getValue()));

            TemplateUtil.filePrint(root, Conguration.tempFilePath, "dao.ftl", Conguration.outDaoPath, name + "Dao.java");
        }
    }
}
