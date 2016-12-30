package ${packageName};

import java.util.List;
import org.crazycake.jdbcTemplateTool.JdbcTemplateTool;
import org.springframework.beans.factory.annotation.Autowired;
/**
*  Created by ${author}
*  Version 1.0
*/

public class BaseDao<T> {

	@Autowired
	private JdbcTemplateTool jdbcTemplateTool;

    /** 保存实体*/
	public void save(T po) throws Exception{
		return jdbcTemplateTool.save(po);
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
	public T get(Class clazz, Object id) throws Exception{
    	jdbcTemplateTool.get(clazz, id);
	}


	/** 查询满足条件的数目 */
	public int getCount(T po) throws Exception {
        SqlParamsPairs pairs = ModelSqlExtUtil.getSelectFromObject(po);
		String sql = countSql = "select count(*) from ( "+pairs.getSql()+" ) as tmp_tab ";
		return jdbcTemplateTool.count(sql, pairs.getParams());
	}

    /** 查询满足条件的实体列表 */
	public List<T> getPage(T po, int offSet, int pageSize) throws Exception {
        SqlParamsPairs pairs = ModelSqlExtUtil.getSelectFromObject(po);
        String sql = countSql = pairs.getSql()+" limit "+offSet+", "+pageSize;
		return list(sql, pairs.getParams(), po.getClass());
    }

    /**
     * 根据sql查询数据列表
     * @param sql	sql
     * @param params	参数
     * @param clazz		目标对象
     * @return <T> List<T>
     */
      public <T> List<T> list(String sql,Object[] params,Class<T> clazz) throws Exception{
		  return jdbcTemplateTool.list(sql, params, clazz);
      }
}