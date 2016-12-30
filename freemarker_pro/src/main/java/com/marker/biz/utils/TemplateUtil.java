package com.marker.biz.utils;


import com.alibaba.fastjson.JSONObject;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenwei23 on 2016/12/13.
 */
public final class TemplateUtil {
    private static final Logger logger = LoggerFactory.getLogger(TemplateUtil.class);
    private static Configuration cfg = null;

    public static Template getTemplate(String tempName) {
        if (StringUtils.isBlank(tempName)) {
            logger.info("getTemplate|param empty error");
            return null;
        }

        try {
            cfg = new Configuration();
            cfg.setTemplateLoader(new ClassTemplateLoader(TemplateUtil.class, "/"));

            Template template = cfg.getTemplate(tempName);
            return template;
        } catch (IOException e) {
            logger.error("getTemplate|exception:" + e);
            return null;
        }

    }


    public static void print(Map<String, Object> map, String tempPath, String tempName) {
        if (!tempPath.endsWith("/")) {
            tempPath = tempPath + "/";
        }

        try {
            Template tmp = getTemplate(tempPath + tempName);
            tmp.process(map, new PrintWriter(System.out));
        } catch (Exception e) {
            logger.error("print|exception:" + e);
        }
    }

    public static void filePrint(Map<String, Object> map, String tempPath, String tempName, String outPath, String outFileName) {
        logger.info("filePrint|param:" + JSONObject.toJSONString(map) + "|tempPath=" + tempPath + "|tempName=" + tempName +
                "|outFileName=" + outFileName + "|outPath=" + outPath);

        if (StringUtils.isBlank(outFileName) || StringUtils.isBlank(tempName)) {
            logger.info("param empty error");
            return;
        }

        FileWriter out = null;
        try {
            File javaFile = new File(outPath);
            //  deleteFile(javaFile);

            if (!javaFile.exists()) {
                javaFile.mkdirs();
            }

            if (!outPath.endsWith("/")) {
                outPath = outPath + "/";
            }
            if (!tempPath.endsWith("/")) {
                tempPath = tempPath + "/";
            }
            out = new FileWriter(new File(outPath + outFileName));
            Template temp = getTemplate(tempPath + tempName);
            temp.setEncoding("UTF-8");
            temp.process(map, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.error("filePrint|exception:" + e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    /**
     * 通过递归调用删除一个文件夹及下面的所有文件
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (file.exists()) {//判断文件是否存在
            if (file.isFile()) {//判断是否是文件
                file.delete();//删除文件
            } else if (file.isDirectory()) {//否则如果它是一个目录
                File[] files = file.listFiles();//声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) {//遍历目录下所有的文件
                    deleteFile(files[i]);//把每个文件用这个方法进行迭代
                }
                file.delete();//删除文件夹
            }
        }
    }


    public static void main(String[] args) {
        String tempName = "test.ftl";
        String outPath = "project/jsp/";
        String outFileName = "test.html";
        String tempPath = "/tempfile";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", "chenwei");
        map.put("age", 15);
      /*TemplateUtil.print(map, tempPath, tempName);

      TemplateUtil.filePrint(map, tempPath, tempName, outPath, outFileName);*/

        String fileStr = "project/jsp";
        deleteFile(new File(fileStr));

    }
}
