package com.marker.biz.service;

import com.alibaba.fastjson.JSONObject;
import com.marker.biz.common.MySqlDataType;
import com.marker.biz.domain.Attr;
import com.marker.biz.domain.Column;
import com.marker.biz.utils.CamelNameUtil;
import com.marker.biz.utils.JDBCUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by chenwei23 on 2016/12/15.
 * 主要是将数据库中的表转换为实体
 */
public class Table2EntityService {
    private static final Logger logger = LoggerFactory.getLogger(Table2EntityService.class);

    /**
     * 获取表的实体
     * @param jdbcUrl
     * @param username
     * @param password
     * @return
     */
    public Map<String, List<Attr>> getTable2Entity(String jdbcUrl, String username, String password){
        logger.info("getTable2Entity|param:jdbcUrl="+jdbcUrl+"|username="+username+"|password= no show");
        if(StringUtils.isBlank(jdbcUrl) || StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            logger.info("getTable2Entity|param empty error");
            return null;
        }

        try {
            Connection con  = JDBCUtil.getConnection(jdbcUrl, username, password);
            List<String> tableList = JDBCUtil.getTables(con, null, null, null);
            Map<String, List<Attr>> map = new HashMap<String, List<Attr>>();

            if(tableList != null && tableList.size() > 0){
                for(String tabName: tableList){
                    String tb = CamelNameUtil.underscore2camel(tabName);
                    List<Column> columnList = JDBCUtil.getTableColumns(con, null, null, tabName);
                    List<Attr> atList = changeColumn2Attr(columnList);
                    map.put(tb, atList);
                }
            }
            logger.info("getTable2Entity|return:", JSONObject.toJSONString(map));
            return map;
        } catch (Exception e) {
            logger.error("getTable2Entity|exception:",e);
            return null;
        }
    }


    /**
     * 获取表的主键信息
     * @param jdbcUrl
     * @param username
     * @param password
     * @return
     */
    public Map<String, List<Attr>> getTablePrimaryKey(String jdbcUrl, String username, String password){
        logger.info("getTablePrimaryKey|param:jdbcUrl="+jdbcUrl+"|username="+username+"|password= no show");
        if(StringUtils.isBlank(jdbcUrl) || StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            logger.info("getTablePrimaryKey|param empty error");
            return null;
        }

        try {
            Connection con  = JDBCUtil.getConnection(jdbcUrl, username, password);
            List<String> tableList = JDBCUtil.getTables(con, null, null, null);
            Map<String, List<Attr>> map = new HashMap<String, List<Attr>>();

            if(tableList != null && tableList.size() > 0){
                for(String tabName: tableList){
                    String tb = CamelNameUtil.underscore2camel(tabName);
                    List<Column> columnList = JDBCUtil.getPrimaryKeyColumns(con, null, null, tabName);
                    List<Attr> atList = changeColumn2Attr(columnList);
                    map.put(tb, atList);
                }
            }
            logger.info("getTablePrimaryKey|return:", JSONObject.toJSONString(map));
            return map;
        } catch (Exception e) {
            logger.error("getTablePrimaryKey|exception:",e);
            return null;
        }
    }



    /**
     * 将数据库列属性变成实体需求的属性样式
     * @param list
     * @return
     */
    private List<Attr> changeColumn2Attr(List<Column> list){
        logger.info("changeColumn2Attr|param:list="+JSONObject.toJSONString(list));
        if(list == null || list.size() == 0){
            logger.info("changeColumn2Attr|param empty error");
            return null;
        }

        try {
            List<Attr> attrList = new LinkedList<Attr>();
            MySqlDataType[] types = MySqlDataType.values();

            for(Column col:list){
                Attr attr = new Attr();
                String colName = col.getColumnName();
                attr.setField(CamelNameUtil.underscore2camel(colName));
                attr.setMarker(CamelNameUtil.underscore2camel(col.getRemark()));
                for(int i=0;i<types.length;i++){
                    if(types[i].getMySqlDataType().equalsIgnoreCase(col.getTypeName())){
                        attr.setJavaType(types[i].getJavaDataType());
                        attr.setIbatisJdbcType(types[i].getIbatisJdbcType());
                        break;
                    }
                }
                if(StringUtils.isBlank(colName) || StringUtils.isBlank(attr.getJavaType())){
                    continue;
                }
                attrList.add(attr);
            }
            return attrList;
        } catch (Exception e) {
            logger.error("changeColumn2Attr|exception:",e);
            return  null;
        }
    }
}
