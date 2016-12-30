package com.marker.biz.service;

import com.alibaba.fastjson.JSONObject;
import com.marker.biz.common.Conguration;
import com.marker.biz.domain.Attr;
import com.marker.biz.utils.DateUtil;
import com.marker.biz.utils.TemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenwei23 on 2016/12/17.
 * 生成service接口
 */
public class GenerateBizService {
    private static Logger logger = LoggerFactory.getLogger(GenerateBizService.class);

    public void generateBizInterface(Map<String, List<Attr>> map){
        logger.info("generateBizInterface|param:attrMap=", JSONObject.toJSONString(map));
        if(map == null){
            logger.info("generateBizInterface|param empty error");
            return;
        }

        // 判断
        TemplateUtil.deleteFile(new File(Conguration.outServicePath));

        Map<String,Object> root;
        for(Map.Entry<String, List<Attr>> entry:map.entrySet()){
            root = new HashMap<String, Object>();
            root.put("packageName", Conguration.packagePath+".service");
            root.put("author", Conguration.author);

            String className = entry.getKey();
            char[] cs=className.toCharArray();
            cs[0]-=32;
            String name =  String.valueOf(cs);
            System.out.println(name);
            root.put("entityName", name);
            root.put("importPackage", Conguration.packagePath+".domain"+"."+name);
            root.put("attrs", entry.getValue());
            root.put("date", DateUtil.getDate1());
            logger.info(JSONObject.toJSONString(entry.getValue()));

            TemplateUtil.filePrint(root, Conguration.tempFilePath, "service.ftl", Conguration.outServicePath, name + "Service.java");
        }
    }

    /**
     * 生成serviceImpl实现
     * @param map
     */
    public void generateBizInterfaceImpl(Map<String, List<Attr>> map){
        logger.info("generateBizInterfaceImpl|param:attrMap=", JSONObject.toJSONString(map));
        if(map == null){
            logger.info("generateBizInterfaceImpl|param empty error");
            return;
        }

        // 判断
        TemplateUtil.deleteFile(new File(Conguration.outServiceImplPath));

        Map<String,Object> root;
        for(Map.Entry<String, List<Attr>> entry:map.entrySet()){
            root = new HashMap<String, Object>();
            root.put("packagePath", Conguration.packagePath+".service.impl");
            root.put("author", Conguration.author);

            String className = entry.getKey();
            char[] cs=className.toCharArray();
            cs[0]-=32;
            String name =  String.valueOf(cs);
            System.out.println(name);
            root.put("entityName", name);
            root.put("attrs", entry.getValue());
            root.put("domainPackage", Conguration.packagePath+".domain."+name);
            root.put("servicePackage",Conguration.packagePath+".service."+name+"Service");
            root.put("daoPackage", Conguration.packagePath+".dao."+name+"Dao");
            root.put("date", DateUtil.getDate1());

            logger.info(JSONObject.toJSONString(entry.getValue()));

            TemplateUtil.filePrint(root, Conguration.tempFilePath, "serviceImpl.ftl", Conguration.outServiceImplPath, name + "ServiceImpl.java");
        }
    }
}
