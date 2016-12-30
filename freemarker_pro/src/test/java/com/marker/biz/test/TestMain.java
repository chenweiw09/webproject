package com.marker.biz.test;

import com.marker.biz.common.Conguration;
import com.marker.biz.domain.Attr;
import com.marker.biz.utils.TemplateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenwei23 on 2016/12/14.
 */
public class TestMain {
    public static void main(String[] args){
        List<Object> list = new ArrayList<Object>();
        list.add(new Attr("username", "String"));
        list.add(new Attr("password", "String"));
        list.add(new Attr("age", "int"));
        list.add(new Attr("hobby", "String"));

        Map<String,Object> root = new HashMap<String, Object>();
        root.put("packageName", Conguration.packagePath);
        root.put("className", "User");
        root.put("attrs", list);
        root.put("author", Conguration.author);

        String tempName = "bean.ftl";
        TemplateUtil.filePrint(root, Conguration.tempFilePath, tempName, Conguration.outDomainPath, "User.java");


    }
}
