package ${packagePath};

import java.util.List;
import org.crazycake.jdbcTemplateTool.JdbcTemplateTool;
import org.crazycake.jdbcTemplateTool.model.SqlParamsPairs;
import org.springframework.beans.factory.annotation.Autowired;
import ${utilPackagePath};
/**
*  Created by ${author} on ${date}
*  Version 1.0
*/

public class BaseDao<T> {

	@Autowired
	private JdbcTemplateTool jdbcTemplateTool;

    /** 保存实体*/
	public void save(T po) throws Exception{
		jdbcTemplateTool.save(po);
	}

    /** 删除实体 */
	public void delete(T po) throws Exception{
    	jdbcTemplateTool.delete(po);
	}

    /** 更新实体 */
	public void update(T po) throws Exception{
    	jdbcTemplateTool.update(po);
    }

    /** 批量更新实体 */
	public void batchUpdate(String sql, List<Object[]> paramList) throws Exception{
    	jdbcTemplateTool.batchUpdate(sql, paramList);
	}

    /** 根据主键查询实体 */
	public T get(Class<T> clazz, Object id) throws Exception{
    	return jdbcTemplateTool.get(clazz, id);
	}


	/** 查询满足条件的数目 */
	public int getCount(T po) throws Exception {
        SqlParamsPairs pairs = ModelSqlExtUtil.getSelectFromObject(po);
		String countSql = "select count(*) from ( "+pairs.getSql()+" ) as tmp_tab ";
		return jdbcTemplateTool.count(countSql, pairs.getParams());
	}

    /** 查询满足条件的实体列表 */
	public List getPage(T po, int offSet, int pageSize) throws Exception {
        SqlParamsPairs pairs = ModelSqlExtUtil.getSelectFromObject(po);
        String countSql = pairs.getSql()+" limit "+offSet+", "+pageSize;
		return (List<T>)jdbcTemplateTool.list(countSql, pairs.getParams(), po.getClass());
    }

    /**
     * 根据sql查询数据列表
     * @param sql	sql
     * @param params	参数
     * @param clazz		目标对象
     * @return <T> List<T>
     */
      public List<T> list(String sql,Object[] params,Class<T> clazz) throws Exception{
		  return jdbcTemplateTool.list(sql, params, clazz);
      }
}