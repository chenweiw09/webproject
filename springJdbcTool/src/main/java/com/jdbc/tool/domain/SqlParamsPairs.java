package com.jdbc.tool.domain;

import java.util.Arrays;

/**  
 * @author chenwei  
 * @date 创建时间：2016年11月4日 下午3:23:15 
 * @version 1.0  用与保存标准化的SQL语句和变量参数数组
 */

public class SqlParamsPairs {
	private String sql;
	private Object[] params;
	
	public SqlParamsPairs(){}
	
	public SqlParamsPairs(String sql, Object[] params){
		this.sql = sql;
		this.params = params;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

	@Override
	public String toString() {
		return "SqlParamsPairs [sql=" + sql + ", params="
				+ Arrays.toString(params) + "]";
	}
}
