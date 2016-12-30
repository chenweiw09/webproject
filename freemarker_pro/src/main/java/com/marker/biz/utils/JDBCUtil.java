package com.marker.biz.utils;

import com.marker.biz.domain.Column;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

/**
 * Created by chenwei23 on 2016/12/15.
 */
public class JDBCUtil {
    private static final Logger logger = LoggerFactory.getLogger(JDBCUtil.class);

    private static Connection con = null;
    private static Object obj = new Object();

    /**
     * 获取数据库连接
     * @param jdbcUrl
     * @param username
     * @param password
     * @return
     */
    public static Connection getConnection(String jdbcUrl, String username, String password){
        logger.debug("getConnection|param:jdbcUrl="+jdbcUrl+"username="+username+"password"+password);
        jdbcUrl = "jdbc:mysql://"+jdbcUrl;
        try {
            if(con == null){
                synchronized (obj){
                    if(con == null){
                        Class.forName("com.mysql.jdbc.Driver");
                        con = DriverManager.getConnection(jdbcUrl, username, password);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("getConnection|exception", e);
        }
        return con;
    }


    /**
     * 获取数据库的catalog, 从概念上说，一个数据库系统包含多个Catalog，每个Catalog又包含多个Schema，而每个Schema又包含多个数 据库对象（表、视图、字段等），
     * 反过来讲一个数据库对象必然属于一个Schema，而该Schema又必然属于一个Catalog，这样我们就可以得到该数据库对象的完全限定名称从而解决命名冲突的问题了；
     * @param con
     * @return
     * @throws SQLException
     */
    public static List<String> getCatalogs(Connection con) throws SQLException {
        DatabaseMetaData dm = con.getMetaData();
        ResultSet rs = null;
        List<String> list = new LinkedList<String>();
        try {
            rs = dm.getCatalogs();
            while(rs.next()){
                list.add(rs.getString(1));
            }

        } finally {
            if(rs != null){
                rs.close();
            }
        }
        return list;
    }

    public static String getCatalog(Connection con) throws SQLException {
        DatabaseMetaData dm = con.getMetaData();
        ResultSet rs = null;
        String catalog = "";
        try {
            rs = dm.getCatalogs();
            if(rs.next()){
                catalog = rs.getString(1);
            }
        } finally {
            if(rs != null){
                rs.close();
            }
        }
        return catalog;
    }

    /**
     * 方案（Schema）为数据库对象的集合,这些类似用户名的节点其实就是一个schema，schema里面包含了各种对象如tables,
     * views, sequences, stored procedures, synonyms, indexes, clusters, and database links。
     * 一个用户一般对应一个schema,该用户的schema名等于用户名，并作为该用户缺省schema。
     * @param con
     * @return
     * @throws SQLException
     */
    public static Map<String, List> getSchemas(Connection con) throws SQLException {
        DatabaseMetaData dm = con.getMetaData();
        ResultSet rs = null;
        Map<String, List> map = new HashMap<String, List>();
        List list;

        try {
            rs = dm.getSchemas();
            while(rs.next()){
                String schema = rs.getString(1);
                String catalog = null;
                if(rs.getMetaData().getColumnCount() > 1){
                    catalog = rs.getString(2);
                }
                list = (List) map.get(catalog);
                if(list == null){
                    list = new LinkedList();
                    map.put(catalog, list);
                }
                list.add(schema);
            }
            return map;

        } finally {
            if (rs != null)
                rs.close();
        }
    }

    /**
     * 获取数据库表名
     * @param con
     * @param catalog
     * @param schema
     * @param tablePattern
     * @return
     * @throws SQLException
     */
    public static List<String> getTables(Connection con, String catalog, String schema, String tablePattern) throws SQLException {
        logger.debug("catalog='" + catalog + "'");
        logger.debug("schema='" + schema + "'");
        logger.debug("table='" + tablePattern + "'");
        DatabaseMetaData dm = con.getMetaData();
        ResultSet rs = null;

        try {
            rs = dm.getTables(catalog, schema, tablePattern, new String[]{"TABLE", "VIEW", "SYNONYM", "ALIAS"});
            List<String> list = new LinkedList<String>();
            while(rs.next()){
                list.add(rs.getString(3));
            }
            return list;
        } finally {
            if (rs != null)
                rs.close();
        }
    }

    /**
     * 获取外键
     * @param con
     * @param catalog
     * @param schema
     * @param table
     * @return
     * @throws SQLException
     */
    public static Set getForeignKeyColumns(Connection con, String catalog, String schema, String table) throws SQLException {
        logger.debug("catalog='" + catalog + "'");
        logger.debug("schema='" + schema + "'");
        logger.debug("table='" + table + "'");
        DatabaseMetaData dmd = con.getMetaData();
        ResultSet rs = null;
        try {
            rs = dmd.getImportedKeys(catalog, schema, table);
            HashSet columns = new HashSet();
            while (rs.next()) {
                columns.add(rs.getString(8));
            }
            return columns;
        }
        finally {
            if (rs != null) rs.close();
        }
    }

    /**
     * 获取主键
     * @param con
     * @param catalog
     * @param schema
     * @param table
     * @return
     * @throws SQLException
     */
    public static List<Column> getPrimaryKeyColumns(Connection con, String catalog, String schema, String table) throws SQLException {
        logger.debug("catalog='" + catalog + "'");
        logger.debug("schema='" + schema + "'");
        logger.debug("table='" + table + "'");
        DatabaseMetaData dmd = con.getMetaData();
        ResultSet rs = null;
        try {
            rs = dmd.getPrimaryKeys(catalog, schema, table);

            List<Column> pkColumns = new LinkedList<Column>();
            while (rs.next()) {
                List tmp = getTableColumns(con, catalog, schema, table, rs.getString(4));
                Column pkColumn = (Column)tmp.get(0);
                pkColumns.add(pkColumn);
            }
            return pkColumns;
        }
        finally {
            if (rs != null) rs.close();
        }
    }


    public static List<Column> getTableColumns(Connection con, String catalog, String schema, String table) throws SQLException  {
        return getTableColumns(con, catalog, schema, table, null);
    }

    public static List<Column> getTableColumns(Connection con, String catalog, String schema, String table, String columnPattern) throws SQLException {
        logger.debug("catalog='" + catalog + "'");
        logger.debug("schema='" + schema + "'");
        logger.debug("table='" + table + "'");
        logger.debug("column='" + columnPattern+ "'");
        DatabaseMetaData dmd = con.getMetaData();
        ResultSet rs = null;

        try{
            rs = dmd.getColumns(catalog, schema, table, columnPattern);
            List<Column> columns = new LinkedList<Column>();
            while(rs.next()){
                Column col = new Column();
                col.setColumnName(rs.getString(4));
                col.setTypeName(rs.getString(6));
                col.setSqlDataType(rs.getShort(5));
                col.setSqlNotNull("No".equals(rs.getString(18)));
                col.setRemark(rs.getString(12));
                col.setDefaultValue(rs.getString(13));
                columns.add(col);
            }
            return columns;
        }finally {
            if (rs != null){
                rs.close();
            }
        }
    }

}
