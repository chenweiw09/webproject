package ${packageName};

import ${importPackage};
import java.util.List;

/**
*  Created by ${author} on ${date}
*  Version 1.0
*/

public interface ${entityName}Service {

	/** 保存实体*/
	public void save${entityName}(${entityName} entity);

	/** 查询满足条件的数目 */
	public int getCount(${entityName} entity);

	/** 分页查询*/
	public List<${entityName}> getPage${entityName}(${entityName} entity, int pageNo, int pageSize);

	/** 更新实体 */
	public void update${entityName}(${entityName} entity);

	/** 删除实体 */
	public void del${entityName}(${entityName} entity);

	<#if attrs?? && (attrs?size > 0) >
    /** 根据主键删除实体 */
    public void del${entityName}By<#list attrs as a>${a.field?cap_first}</#list>(<#list attrs as a>${a.javaType} ${a.field}<#if a_has_next>, </#if></#list>);

    /** 根据主键查询实体 */
    public ${entityName} get${entityName}By<#list attrs as a>${a.field?cap_first}</#list>(<#list attrs as a>${a.javaType} ${a.field}<#if a_has_next>, </#if></#list>);

	</#if>

}