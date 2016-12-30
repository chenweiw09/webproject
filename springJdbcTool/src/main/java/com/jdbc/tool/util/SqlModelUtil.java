package com.jdbc.tool.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdbc.tool.domain.SqlParamsPairs;

/**  
 * @author chenwei  
 * @date 创建时间：2016年11月4日 下午2:28:31 
 * @version 1.0  
 * 主要功能是通过对实体的反射，获取相对应的查询语句。
 * 此文件主要参考于https://github.com/alexxiyang/jdbctemplatetool.git
 */

public class SqlModelUtil {
	private static Logger logger = LoggerFactory.getLogger(SqlModelUtil.class);
	
	/**
	 * get the insert sql from the entity
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public static <T> SqlParamsPairs getInsertFromObject(T po) throws Exception{
		//用来存放insert语句
		StringBuffer insertSql = new StringBuffer();
		//用来存放?号的语句
		StringBuffer paramsSql = new StringBuffer();
		//用来存放参数值
		List<Object> params = new ArrayList<Object>();
		//分析表名
		String tableName = getTableName(po.getClass());
		insertSql.append("insert into "+tableName + "( ");
		
		int count = 0;
		//分析列
		Field[] fields = po.getClass().getDeclaredFields();
		for(Field f : fields){
			//获取具体参数值
			Method getter = getGetter(po.getClass(), f);
			
			if(getter == null){
				continue;
			}
			
			Object value = getter.invoke(po);
			if(value == null){
				continue;
			}
			
			if(value instanceof Enum){
				value = value.toString();
			}
			
			Transient tranAnno = getter.getAnnotation(Transient.class);
			if(tranAnno != null){
				//如果有 Transient 标记直接跳过
				continue;
			}
			
			//获取字段名
			String columnName = getColumnNameFromGetter(getter, f);
			if(count !=0){
				insertSql.append(",");
				paramsSql.append(",");
			}
			insertSql.append(columnName);
			paramsSql.append("?");
			params.add(value);
			count++;
		}
		insertSql.append(") values (");
		insertSql.append(paramsSql + ")");
		SqlParamsPairs sqlAndParams = new SqlParamsPairs(insertSql.toString(), params.toArray());
		logger.debug(sqlAndParams.toString());
		
		return sqlAndParams;
	}
	
	/**
	 * get update sql from entity
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public static <T> SqlParamsPairs getUpdateFromObject(T po) throws Exception{
		//用来存放insert语句
		StringBuffer updateSql = new StringBuffer();
				
		//用来存放where语句
		StringBuffer whereSql = new StringBuffer();
				
		//用来存放参数值
		List<Object> params = new ArrayList<Object>();
		
		//分析表名
		String tableName = getTableName(po.getClass());
		
		//用来存储id
		Object idValue = null;
				
		updateSql.append("update " + tableName + " set");
				
		//分析列
		Field[] fields = po.getClass().getDeclaredFields();
		
		int count = 0;
		for(Field f:fields){
			//获取具体参数值
			Method getter = getGetter(po.getClass(), f);
			
			if(getter == null){
				continue;
			}
			
			Object value = getter.invoke(po);
			if(value == null){
				continue;
			}
			
			if(value instanceof Enum){
				value = value.toString();
			}
			
			Transient tranAnno = getter.getAnnotation(Transient.class);
			if(tranAnno != null){
				//如果有 Transient 标记直接跳过
				continue;
			}
			
			//获取字段名
			String columnName = getColumnNameFromGetter(getter, f);
			
			// 判断是不是主键
			Id idAnno = getter.getAnnotation(Id.class);
			if(idAnno != null){
				whereSql.append(columnName + " = ?");
				idValue = value;
				continue;
			}
			
			//如果是普通列
			params.add(value);
			if(count != 0){
				updateSql.append(",");
			}
			
			updateSql.append(" "+columnName+" = ?");
			count++;
		}
		updateSql.append(" where ");
		updateSql.append(whereSql);
		params.add(idValue);
		
		SqlParamsPairs sqlAndParams = new SqlParamsPairs(updateSql.toString(),params.toArray());
		logger.debug(sqlAndParams.toString());
		
		return sqlAndParams;
		
	} 
	
	/**
	 * 按照主键删除对象，如果没有主键，则按照对象传入的条件删除
	 * @param po
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoIdAnnotationFoundException 
	 */
	public static SqlParamsPairs getDeleteFromObject(Object po) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		//用来存放insert语句
		StringBuffer deleteSql = new StringBuffer();
				
		//用来存储id
		Object idValue = null;
				
		//分析表名
		String tableName = getTableName(po.getClass());
				
		deleteSql.append("delete from " + tableName + " where ");
				
		Class<?> clazz = po.getClass();
		//分析列
		Field[] fields = clazz.getDeclaredFields();
				
		//用于寻找id字段
		Id idAnno = null;
		for(Field f:fields){
			//获取具体参数值
			Method getter = getGetter(po.getClass(), f);
			
			if(getter == null){
				continue;
			}
			
			// 判断是不是主键
			idAnno = getter.getAnnotation(Id.class);
			if(idAnno == null){
				continue;
			}
			
			//看有没有定义column
			String columnName = getColumnNameFromGetter(getter,f);

			deleteSql.append(columnName + " = ?");
			
			idValue = getter.invoke(po, new Object[]{});
			
			break;
		}
		
		//全部遍历完如果找不到主键就抛异常
		if(idAnno == null){
			throw new RuntimeException(clazz.getName());
		}
		
		SqlParamsPairs sqlAndParams = new SqlParamsPairs(deleteSql.toString(),new Object[]{idValue});
		logger.debug(sqlAndParams.toString());
		return sqlAndParams;
	}
	
	public static <T> SqlParamsPairs getGetFromObject(Class<T> clazz, Object id){
		StringBuffer getSql = new StringBuffer();
		//分析表名
		String tableName = getTableName(clazz);
		
		getSql.append("select "+getTableFieldName(clazz)+" from " + tableName + " where ");
		//分析列
		Field[] fields = clazz.getDeclaredFields();
		Id idAnno = null;
		
		for(Field f: fields){
			//找id字段
			Method getter = getGetter(clazz,f);
			
			if(getter == null){
				//没有get方法直接跳过
				continue;
			}
			
			//看是不是主键
			idAnno = getter.getAnnotation(Id.class);
			if(idAnno == null){
				continue;
			}
			
			//get column name
			String columnName = getColumnNameFromGetter(getter,f);

			getSql.append(columnName + " = ?");
			
			break;
		}
		
		//全部遍历完如果找不到主键就抛异常
		if(idAnno == null){
			throw new RuntimeException(clazz.getName() +"has no Id annotation");
		}
				
		SqlParamsPairs sqlAndParams = new SqlParamsPairs(getSql.toString(),new Object[]{id});
		logger.debug(sqlAndParams.toString());
				
		return sqlAndParams;
	}
	
	
	/**
	 * 获取实体的名字
	 * @param obj
	 * @return
	 */
	static <T> String getTableName(Class<T> clazz){
		Table tableAnno = clazz.getAnnotation(Table.class);
		//如果有Table注解
		if(tableAnno != null){
			if(tableAnno.catalog() != null){
				return tableAnno.catalog() + "." + tableAnno.name();
			}
			return tableAnno.name();
		}
		
		// no table annotation
		String name = clazz.getName();
		return CamelNameUtils.camel2underscore(name.substring(name.lastIndexOf(".")+1));		
		
	}
	
	//
	
	static String getColumnNameFromGetter(Method getter, Field f){
		String columnName = "";
		Column columnAnno = getter.getAnnotation(Column.class);
		if(columnAnno != null){
			columnName = columnAnno.name();
		}
		
		if(columnName == null || "".equals(columnName)){
			//如果没有列注解就用命名方式去猜
			columnName = CamelNameUtils.camel2underscore(f.getName());
		}
		
		return columnName;
	}
	
	static <T> Method getGetter(Class<T> clazz, Field f){
		String getterName = "get" + CamelNameUtils.capitalize(f.getName());
		Method getter = null;
		if("serialVersionUID".equals(f.getName())){
			return null;
		}
		try {
			getter = clazz.getMethod(getterName);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return getter;
	}
	
	
	public static String getTableFieldName(Class<?> clazz){
		Field[] fields=clazz.getDeclaredFields();
		StringBuffer tableFieldName = new StringBuffer();
		for(Field f:fields){
			Method getter = getGetter(clazz, f);
			if(getter == null){
				continue;
			}
			String columnName = getColumnNameFromGetter(getter,f);
			if("serialVersionUID".equals(columnName)){
				continue;
			}
			
			tableFieldName.append(columnName +", ");
		}
		String re = tableFieldName.toString();
		return re.substring(0, re.length()-2);
	}
	
	
	public static void main(String [] args){
		String re = "aa, bb, cc, ee,";
		System.out.println(re.substring(0, re.length()-1));
	}
}
