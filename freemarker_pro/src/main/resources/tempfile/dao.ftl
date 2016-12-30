package ${packageName};

import ${domainPackage};
import java.util.List;
import org.springframework.stereotype.Repository;

/**
*  Created by ${author}
*  Version 1.0
*/
@Repository
public class ${entityName}Dao extends BaseDao<${entityName}>{

	<#if attrs?? && (attrs?size > 0) >
	//目前只支持单一主键查询实例
	public ${entityName} get${entityName}By<#list attrs as a>${a.field?cap_first}</#list>(<#list attrs as a>${a.javaType} ${a.field}<#if a_has_next>, </#if></#list>){
		return this.get(${entityName}.class, <#list attrs as a>${a.field}<#if a_has_next>, </#if></#list>)
	}


    public void del${entityName}By<#list attrs as a>${a.field?cap_first}</#list>(<#list attrs as a>${a.javaType} ${a.field}<#if a_has_next>, </#if></#list>){
		String sql = "DELETE FROM ${tableName} WHERE <#list attrs as a> ${a.field} = "+ ${a.field} <#if a_has_next> " AND " </#if></#list>;
		Object[] params = new Object[]{<#list attrs as a>${a.field}<#if a_has_next>, </#if></#list>};
		this.getJdbcTemplate().update(sql, params);
	}
	</#if>
}