package com.jdbc.tool.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.jdbc.tool.common.NoColumnAnnotationException;
import com.jdbc.tool.common.NoIdAnnotationException;
import com.jdbc.tool.domain.SqlParamsPairs;
import com.jdbc.tool.util.BatchUpdateSetter;
import com.jdbc.tool.util.CatalogUtils;
import com.jdbc.tool.util.IdUtils;
import com.jdbc.tool.util.SqlModelUtil;

/**  
 * @author chenwei  
 * @date 创建时间：2016年11月10日 下午4:26:29 
 * @version 1.0  
 * 此文件部分参考于https://github.com/alexxiyang/jdbctemplatetool.git
 */

public class JdbcTemplateTool {
	private Logger logger = LoggerFactory.getLogger(JdbcTemplateTool.class);
	
	private JdbcTemplate jdbcTemplate;
	
	private JdbcTemplateProxy _proxy;
	
	private JdbcTemplateProxy getProxy(){
		if(_proxy == null){
			_proxy = new JdbcTemplateProxy();
			_proxy.setJdbcTemplate(jdbcTemplate);
		}
		return _proxy;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> list(String sql, Object[] params, Class<T> clazz){
		List<T> list = null;
		if(params == null || params.length == 0){
			list = getProxy().query(sql, new BeanPropertyRowMapper(clazz));
		}else{
			list = getProxy().query(sql, params, new BeanPropertyRowMapper(clazz));
		}
		return list;
	}
	
	
	/**
	 * 分页查询
	 * @param sql
	 * @param params
	 * @param clazz
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public <T> List<T> getPageList(String sql, Object[] params, Class<T> clazz, int pageNo,int pageSize){
		sql = sql + " limit "+pageSize*(pageNo-1)+","+pageSize;
		return this.list(sql, params, clazz);
	}
	
	/**
	 * 获取查询的item数目
	 * @param sql
	 * @param params
	 * @return
	 */
	public int count(String sql, Object[] params){
		int rowCount = 0;
		try {
			Map<String, Object> resultMap = null;
			if(params == null || params.length == 0){
				resultMap = getProxy().queryForMap(sql);
			}else {
				resultMap = getProxy().queryForMap(sql, params);
			}
			Iterator<Map.Entry<String, Object>> it = resultMap.entrySet().iterator();
			if(it.hasNext()){
				Map.Entry<String, Object> entry = it.next();
				rowCount = ((Long)entry.getValue()).intValue();
			}
		} catch (DataAccessException e) {
			logger.info("query count error: sql"+sql, e);
		}
		
		return rowCount;
	}
	
	/**
	 * 获取查询的item数目
	 * @param sql
	 * @param params
	 * @return
	 */
	public int getCount(String sql, Object[] params){
		sql = CatalogUtils.changeCatalog(sql);
		String countSql = "select count(*) from ( "+sql+" ) as tmp_tab ";
		try {
			int n = jdbcTemplate.queryForObject(countSql, params, Integer.class);
			return n;
		} catch (DataAccessException e) {
			logger.error("Error SQL: " + sql);
			throw e;
		}
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	public <T> T get( Class clazz, Object id) throws NoIdAnnotationException, NoColumnAnnotationException{
		SqlParamsPairs sqlAndParams = SqlModelUtil.getGetFromObject(clazz, id);
		
		List<T> list = this.list(sqlAndParams.getSql(), sqlAndParams.getParams(), clazz);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	// ---------------------------- update -----------------------------------//
	public void update(Object po) throws Exception {
		SqlParamsPairs sqlAndParams = SqlModelUtil.getUpdateFromObject(po);
		getProxy().update(sqlAndParams.getSql(), sqlAndParams.getParams());
	}
	
	public void batchUpdate(String sql, List<Object[]> paramList){
		
		BatchUpdateSetter batchUpdateSetter = new BatchUpdateSetter(paramList);
		getProxy().batchUpdate(sql, batchUpdateSetter);
	}
	
	// ---------------------------- save -----------------------------------//
	public long save(Object po) throws Exception{
		String autoGeneratedColumnName = IdUtils.getAutoGeneratedId(po);
		long idValue;
		if(!"".equals(autoGeneratedColumnName)){
			//有自增字段
			idValue = save(po, autoGeneratedColumnName);
			IdUtils.setAutoIncreamentIdValue(po,autoGeneratedColumnName,idValue);
			return idValue;
		}else{
			SqlParamsPairs sqlAndParams = SqlModelUtil.getInsertFromObject(po);
			getProxy().update(sqlAndParams.getSql(), sqlAndParams.getParams());
			return -1L;
		}
		
	}
	
	private long save(Object po, String autoGeneratedColumnName) throws Exception{
		SqlParamsPairs sqlAndParams = SqlModelUtil.getInsertFromObject(po);
		String sql = sqlAndParams.getSql();
		return getProxy().insert(sql, sqlAndParams.getParams(), autoGeneratedColumnName);
	}
	
	// ---------------------------- delete -----------------------------------//
	public void delete(Object po) throws Exception{
		SqlParamsPairs sqlAndParams = SqlModelUtil.getDeleteFromObject(po);
		String sql = sqlAndParams.getSql();
		getProxy().update(sql, sqlAndParams.getParams());
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
}
