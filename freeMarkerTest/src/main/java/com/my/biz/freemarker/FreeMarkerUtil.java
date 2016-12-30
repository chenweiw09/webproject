package com.my.biz.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author chenwei
 * @date 创建时间：2016年11月4日 下午10:48:41
 * @version 1.0
 */

public class FreeMarkerUtil {
	private Configuration cfg = null;

	public Template getTemplate(String name) {
		try {
			cfg = new Configuration();
			 cfg.setTemplateLoader(new ClassTemplateLoader(FreeMarkerUtil.class));

			Template template = cfg.getTemplate(name);
			return template;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void print(String name, Map<String, Object> map){
		Template tmp = this.getTemplate(name);
		try {
			tmp.process(map, new PrintWriter(System.out));
		} catch (TemplateException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void filePrint(String name, Map<String, Object> map, String outPath){
		FileWriter out = null;
		
		try {
			out = new FileWriter(new File(outPath));
			Template temp = this.getTemplate(name);
	        temp.process(map, out);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	
	public static void main(String [] args){
		FreeMarkerUtil f = new FreeMarkerUtil();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user.username", "chenwei");
		map.put("user.age", 15);
		f.print("01.ftl", map);
	}
}
