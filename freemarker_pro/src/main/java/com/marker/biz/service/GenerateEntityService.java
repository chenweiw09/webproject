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
 * Created by chenwei23 on 2016/12/16.
 */
public class GenerateEntityService {
    private static Logger logger = LoggerFactory.getLogger(GenerateEntityService.class);

    public void generateEntity(Map<String, List<Attr>> map){
        logger.info("generateEntity|param:attrMap=", JSONObject.toJSONString(map));
        if(map == null){
            logger.info("generateEntity|param empty error");
            return;
        }

        // 判断
        TemplateUtil.deleteFile(new File(Conguration.outDomainPath));

        Map<String,Object> root = null;
        for(Map.Entry<String, List<Attr>> entry:map.entrySet()){
            root = new HashMap<String, Object>();
            root.put("packageName", Conguration.packagePath+".domain");

            String className = entry.getKey();
            char[] cs=className.toCharArray();
            cs[0]-=32;
            String name =  String.valueOf(cs);
            System.out.println(name);

            root.put("className", name);
            root.put("attrs", entry.getValue());
            root.put("author", Conguration.author);
            logger.info(JSONObject.toJSONString(entry.getValue()));

            TemplateUtil.filePrint(root, Conguration.tempFilePath, "bean.ftl", Conguration.outDomainPath, name + ".java");
        }

    }

}
