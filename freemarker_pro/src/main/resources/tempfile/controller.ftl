package ${packagePath};

import ${domainPackage};
import java.util.List;
import ${servicePackage};
import ${commonPackage};
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
/**
*  Created by ${author} on ${date}
*  Version 1.0
*/
@RequestMapping("/${entityName?uncap_first}")
@Controller
public class ${entityName}Controller{
	private static final Logger logger = LoggerFactory.getLogger(${entityName}Controller.class);

	@Autowired
	private ${entityName}Service ${entityName?uncap_first}Service;

	@RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public CommResult<PageSet<List<${entityName}>>> list${entityName}(${entityName} ${entityName?uncap_first}, HttpServletRequest request){
		String pageSizeStr = request.getParameter("pageSize");
		String currPageStr = request.getParameter("currPage");
		logger.info("list${entityName}|查询实体列表|param:pageSize="+pageSizeStr+"|currPage="+currPageStr+"|${entityName?uncap_first}="+JSONObject.toJSONString(${entityName?uncap_first}));

		int pageSize = 10;
		int currPage = 1;
		CommResult<PageSet<List<${entityName}>>> result = null;
		PageSet<List<${entityName}>> pageData = null;
		try{
			if(StringUtils.isNoneBlank(pageSizeStr)){
				pageSize = Integer.parseInt(pageSizeStr);
			}

			if(StringUtils.isNoneBlank(currPageStr)){
				currPage = Integer.parseInt(currPageStr);
			}
			//查询记录数目
			int totalCount = ${entityName?uncap_first}Service.getCount(${entityName?uncap_first});
			if(totalCount == 0){
				currPage = 1;
				result = new CommResult<PageSet<List<${entityName}>>>("00000", "SUCCESS", null);
			}else{
    			List<${entityName}> list = ${entityName?uncap_first}Service.getPage${entityName}(${entityName?uncap_first}, currPage, pageSize);
				pageData = new PageSet<List<${entityName}>>(currPage, pageSize, totalCount, list);
				result = new CommResult<PageSet<List<${entityName}>>>("00000", "SUCCESS", pageData);
			}
			logger.info("list${entityName}|查询实体列表返回|"+JSONObject.toJSONString(result));
		}catch(Exception e){
			result = new CommResult<PageSet<List<${entityName}>>>("10000", "FAIL", null);
			logger.error("list${entityName}|查询实体列表异常|"+e);
		}
		return result;
	}

	<#if attrs?? && (attrs?size > 0) >
	/** 根据主键删除实体 */
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
	@ResponseBody
    public CommResult<String> delete${entityName}(<#list attrs as a>@RequestParam("${a.field}")${a.javaType} ${a.field},</#list> HttpServletRequest request){
        logger.info("delete${entityName}|删除实体|param:<#list attrs as a> ${a.field} = "+${a.field}<#if a_has_next>+ </#if></#list>);
        CommResult<String> result = null;
		try{
			${entityName?uncap_first}Service.del${entityName}By<#list attrs as a>${a.field?cap_first}</#list>(<#list attrs as a>${a.field}<#if a_has_next>, </#if></#list>);
			result = new CommResult<String>("00000", "SUCCESS", "SUC");
			logger.info("delete${entityName}|删除实体|return:"+JSONObject.toJSONString(result));
		}catch(Exception e){
        	result = new CommResult<String>("10000", "FAIL", null);
			logger.error("delete${entityName}|删除实体异常|"+e);
		}
		return result;
	}

    /** 根据主键编辑实体，如果没有回传主键，就表示是添加新的实体 */
	@RequestMapping(value = "/edit", method = {RequestMethod.POST})
    @ResponseBody
    public CommResult<${entityName}> edit${entityName}(<#list attrs as a>@RequestParam("${a.field}")${a.javaType} ${a.field},</#list> HttpServletRequest request){
        logger.info("edit${entityName}|编辑实体|param:<#list attrs as a> ${a.field} = "+${a.field}<#if a_has_next>+ </#if></#list>);
        CommResult<${entityName}> result = null;
        try{
			${entityName} entity = ${entityName?uncap_first}Service.get${entityName}By<#list attrs as a>${a.field?cap_first}</#list>(<#list attrs as a>${a.field}<#if a_has_next>, </#if></#list>);
        	result = new CommResult<${entityName}>("00000", "SUCCESS", entity);
        	logger.info("edit${entityName}|编辑实体|return:"+JSONObject.toJSONString(result));
        }catch(Exception e){
        	result = new CommResult<${entityName}>("10000", "FAIL", null);
        	logger.error("edit${entityName}|编辑实体异常|"+e);
        }
        return result;
    }
	</#if>

    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    @ResponseBody
    public CommResult<String> update${entityName}(${entityName} ${entityName?uncap_first}, HttpServletRequest request){
    	logger.info("update${entityName}|更新实体|param:${entityName?uncap_first} = "+JSONObject.toJSONString(${entityName?uncap_first}));
    	CommResult<String> result = null;
		try{
			${entityName?uncap_first}Service.update${entityName}(${entityName?uncap_first});
			result = new CommResult<String>("00000", "SUCCESS", "SUC");
			logger.info("update${entityName}|更新实体|return:"+JSONObject.toJSONString(result));
		}catch(Exception e){
			result = new CommResult<String>("10000", "FAIL", null);
			logger.error("update${entityName}|更新实体异常|"+e);
		}
		return result;
	}

    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    @ResponseBody
    public CommResult<String> save${entityName}(${entityName} ${entityName?uncap_first}, HttpServletRequest request){
		logger.info("save${entityName}|保存实体|param:${entityName?uncap_first} = "+JSONObject.toJSONString(${entityName?uncap_first}));
        CommResult<String> result = null;
        try{
			${entityName?uncap_first}Service.save${entityName}(${entityName?uncap_first});
            result = new CommResult<String>("00000", "SUCCESS", "SUC");
            logger.info("save${entityName}|保存实体|return:"+JSONObject.toJSONString(result));
        }catch(Exception e){
            result = new CommResult<String>("10000", "FAIL", null);
            logger.error("save${entityName}|保存实体异常|"+e);
        }
        return result;
    }

}