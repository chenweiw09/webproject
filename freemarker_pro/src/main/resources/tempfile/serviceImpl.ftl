package ${packagePath};

/**
*  Created by ${author}
*  Version 1.0
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import ${domainPackage};
import ${servicePackage};
import ${daoPackage};


@Service
public class ${entityName}ServiceImpl implements ${entityName}Service{
	private static final Logger logger = LoggerFactory.getLogger(${entityName}ServiceImpl.class);

	@Autowired
	private ${entityName}Dao ${entityName?uncap_first}Dao;
	
    @Override
	public void save${entityName}(${entityName} entity) {
		logger.info("save${entityName}|param:"+JSONObject.toJSONString(entity));
		if(entity == null){
			logger.info("entity is null and no saving|return:");
			return;
		}
		try{
			${entityName?uncap_first}Dao.save(entity);
			logger.info("save${entityName}|保存实体|return:");
		}catch(Exception e){
			logger.error("save${entityName}|保存实体出现异常|exception:"+e);
		}
	}

	@Override
	public int getCount(${entityName} entity) {
		logger.info("getCount|param:"+JSONObject.toJSONString(entity));
		if(entity == null){
			logger.info("entity is null and no saving|return:0");
			return 0;
		}
		try{
			int n = ${entityName?uncap_first}Dao.getCount(entity);
			logger.info("getCount|获取实例数目|return:"+n);
			return n;
		}catch(Exception e){
			logger.error("getCount|获取实例数目异常|exception:"+e);
			return 0;
		}
	}

	@Override
	public void update${entityName}(${entityName} entity){
		logger.info("update${entityName}|更新实体|param:entity="+JSONObject.toJSONStrin(entity));
		if(entity == null){
			logger.info("update${entityName}|param is empty|return 0");
			return;
		}

		try{
			${entityName?uncap_first}Dao.update(entity);
			logger.info("update${entityName}|更新实体|return:");
		}catch(Exception e){
			logger.error("update${entityName}|更新实体异常|exception:"+e);
		}

	}

	@Override
	public List<${entityName}> getPage${entityName}(${entityName} entity, int pageNo, int pageSize){
		logger.info("getPage${entityName}|param:entity="+JSONObject.toJSONString(entity)+"|pageNo="+pageNo+"|pageSize="+pageSize);
		if(entity==null || pageNo<1 || pageSize<1){
			logger.info("getPage${entityName}|param empty error|return null");
			return null;
		}

		int offSize = (pageNo - 1)*pageSize;
		try{
			List<${entityName}> list = ${entityName?uncap_first}Dao.getPage(entity, offSize, pageSize);
			logger.info("getPage${entityName}|return:"+JSONObject.toJSONString(list));
			return list;
		}catch(Exception e){
			logger.error("getPage${entityName}|根据条件获取实例异常|exception:"+e);
			return null;
		}
	}

	@Override
	public void del${entityName}(${entityName} entity){
		logger.info("del${entityName}|删除实体|param:entity="+JSONObject.toJSONStrin(entity));
		if(entity == null){
			logger.info("del${entityName}|param is empty");
			return;
		}

		try{
			${entityName?uncap_first}Dao.del(entity);
			logger.info("del${entityName}|删除实体|return:");
		}catch(Exception e){
			logger.error("del${entityName}|删除实体异常|exception:"+e);
		}
	}

	<#if attrs?? && (attrs?size > 0) >
	@Override
    public void del${entityName}By<#list attrs as a>${a.field?cap_first}</#list>(<#list attrs as a>${a.javaType} ${a.field}<#if a_has_next>, </#if></#list>){
		logger.info("del${entityName}By<#list attrs as a>${a.field?cap_first}</#list>|param:"+<#list attrs as a>" ${a.field}="+${a.field}<#if a_has_next>+ </#if></#list>);
		/* if(<#list attrs as a>${a.field}==null <#if a_has_next>|| </#if></#list>){
			logger.info("del${entityName}By<#list attrs as a>${a.field?cap_first}</#list>|param empty error|return null");
			return;
		}*/
		try{
			${entityName?uncap_first}Dao.del${entityName}By<#list attrs as a>${a.field?cap_first}</#list>(<#list attrs as a>${a.field}<#if a_has_next>, </#if></#list>);
			logger.info("del${entityName}By<#list attrs as a>${a.field?cap_first}</#list>|return:");
		}catch(Exception e){
			logger.error("del${entityName}By<#list attrs as a>${a.field?cap_first}</#list>|根据主键删除实体异常|exception:"+e);
		}
    }


    @Override
	public ${entityName} get${entityName}By<#list attrs as a>${a.field?cap_first}</#list>(<#list attrs as a>${a.javaType} ${a.field}<#if a_has_next>, </#if></#list>){
		logger.info("get${entityName}By<#list attrs as a>${a.field?cap_first}</#list>|param:",<#list attrs as a>" ${a.field}="+${a.field}<#if a_has_next>+ </#if></#list>);
		/* if(<#list attrs as a>${a.field}==null <#if a_has_next>|| </#if></#list>){
			logger.info("get${entityName}By<#list attrs as a>${a.field?cap_first}</#list>|param empty error|return null");
			return null;
		}*/
		try{
			${entityName} entity = ${entityName?uncap_first}Dao.getBy<#list attrs as a>${a.field?cap_first}</#list>(<#list attrs as a>${a.field}<#if a_has_next>, </#if></#list>);
			logger.info("get${entityName}By<#list attrs as a>${a.field?cap_first}</#list>|return:"+JSONObject.toJSONStrin(entity));
			return entity;
		}catch(Exception e){
			logger.error("get${entityName}By<#list attrs as a>${a.field?cap_first}</#list>|根据主键查询实体异常|exception:"+e);
			return null;
		}
	}

	</#if>
}