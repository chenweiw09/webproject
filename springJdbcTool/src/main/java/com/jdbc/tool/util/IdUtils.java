package com.jdbc.tool.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**  
 * @author chenwei  
 * @date 创建时间：2016年11月10日 下午6:15:47 
 * @version 1.0  
 * @description 根据主键的Id注解，获取主键的字段名(需要添加主键生成的策略)
 */

public class IdUtils {
	
	/**
	 * 获取自增字段的属性名称
	 * @param po
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static String getAutoGeneratedId(Object po) throws NoSuchMethodException, SecurityException{
		String autoGeneratedId = "";
		Field[] fields = po.getClass().getDeclaredFields();
		for(Field f : fields){
			if("serialVersionUID".equals(f.getName())){
				continue;
			}
			
			String getterName = "get" + CamelNameUtils.capitalize(f.getName());
			Method getter = po.getClass().getDeclaredMethod(getterName);
			
			Id idanno = getter.getAnnotation(Id.class);
			if(idanno == null){
				continue;
			}
			
			GeneratedValue generatedValueAnno = getter.getAnnotation(GeneratedValue.class);
			if(generatedValueAnno == null){
				continue;
			}
			
			if(GenerationType.IDENTITY == generatedValueAnno.strategy() || GenerationType.TABLE == generatedValueAnno.strategy()){
				autoGeneratedId = f.getName();
				break;
			}
		}
		return autoGeneratedId;
	}
	
	/**
	 * 将自增主键的值设置到po中。
	 * @param po
	 * @param autoGeneratedId
	 * @param idValue
	 * @throws Exception
	 * @throws NoSuchMethodException
	 */
	public static void setAutoIncreamentIdValue(Object po,String autoGeneratedId,Object idValue) throws Exception, NoSuchMethodException{
		String setterName = "set" + CamelNameUtils.capitalize(autoGeneratedId);
		Method setter = po.getClass().getDeclaredMethod(setterName, idValue.getClass());
		setter.invoke(po, idValue);
	}
	
}
