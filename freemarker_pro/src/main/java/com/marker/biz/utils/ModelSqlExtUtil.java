/*
package com.marker.biz.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.beans.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Table;
import org.crazycake.jdbcTemplateTool.model.SqlParamsPairs;
import org.crazycake.jdbcTemplateTool.utils.ModelSqlUtils;
import org.crazycake.utils.CamelNameUtils;

*/
/**
 * Created by chenwei23 on 2016/12/20.
 *//*

public class ModelSqlExtUtil extends ModelSqlUtils {

    private static Logger logger = LoggerFactory.getLogger(ModelSqlExtUtil.class);

    public static <T> SqlParamsPairs getSelectFromObject(T po) throws Exception {
        // 用来存放select语句
        StringBuffer selectSql = new StringBuffer();
        // 用来存放where语句
        StringBuffer whereSql = new StringBuffer();
        // 如果有主键，则用主键的sql语句代替whereSql
        //StringBuffer idSql = new StringBuffer();
        // 用来存放参数值
        List<Object> params = new ArrayList<Object>();
        // 用来存储id
        //Object idValue = null;
        // 分析表名
        String tableName = getTableName(po.getClass());

        selectSql.append("SELECT ");
        // 计数器
        int countP = 0;
        int count = 0;

        // 分析列
        Field[] fields = po.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];

            if ("serialVersionUID".equals(f.getName())) {
                continue;
            }

            // 获取具体参数值
            Method getter = getGetter(po.getClass(), f);

            if (getter == null) {
                continue;
            }

            Transient tranAnno = getter.getAnnotation(Transient.class);
            if (tranAnno != null) {
                // 如果有 Transient 标记直接跳过
                continue;
            }

            // 获取字段名
            String columnName = getColumnNameFromGetter(getter, f);

            if (countP != 0) {
                selectSql.append(", ");
            }
            selectSql.append(columnName);
            countP++;

            Object value = getter.invoke(po);
            if (value == null) {
                // 如果参数值是null就直接跳过（不允许覆盖为null值，规范要求更新的每个字段都要有值，没有值就是空字符串）
                continue;
            }

            // 看看是不是主键
			*/
/*Id idAnno = getter.getAnnotation(Id.class);
			if (idAnno != null) {
				// 如果是主键
				idSql.append(columnName + " = ?");
				idValue = value;
				continue;
			}*//*


            // 非主键走whereSql
            if (count != 0) {
                whereSql.append(" AND ");
            }
            whereSql.append(columnName + " = ?");

            params.add(value);
            count++;
        }

        selectSql.append(" FROM ").append(tableName).append(" WHERE ");
        SqlParamsPairs sqlAndParams = null;
		*/
/*
		if (idSql != null && idSql.length() > 0) {
			selectSql.append(idSql);
			sqlAndParams = new SqlParamsPairs(selectSql.toString(), new Object[] { idValue });
			logger.debug(sqlAndParams.toString());
			return sqlAndParams;

		}*//*

        selectSql.append(whereSql);
        sqlAndParams = new SqlParamsPairs(selectSql.toString(), params.toArray());
        logger.debug(sqlAndParams.toString());
        return sqlAndParams;
    }

    private static <T> String getTableName(Class<T> clazz) {

        Table tableAnno = clazz.getAnnotation(Table.class);
        if (tableAnno != null) {
            if (tableAnno.catalog() != null) {
                return tableAnno.catalog() + "." + tableAnno.name();
            }
            return tableAnno.name();
        }
        // if Table annotation is null
        String className = clazz.getName();
        return CamelNameUtils.camel2underscore(className.substring(className
                .lastIndexOf(".") + 1));
    }

    private static <T> Method getGetter(Class<T> clazz, Field f) {
        String getterName = "get" + CamelNameUtils.capitalize(f.getName());
        Method getter = null;
        try {
            getter = clazz.getMethod(getterName);
        } catch (Exception e) {
            logger.debug(getterName + " doesn't exist!", e);
        }
        return getter;
    }

    private static String getColumnNameFromGetter(Method getter, Field f) {
        String columnName = "";
        Column columnAnno = getter.getAnnotation(Column.class);
        if (columnAnno != null) {
            // 如果是列注解就读取name属性
            columnName = columnAnno.name();
        }

        if (columnName == null || "".equals(columnName)) {
            // 如果没有列注解就用命名方式去猜
            columnName = CamelNameUtils.camel2underscore(f.getName());
        }
        return columnName;
    }

}
*/
