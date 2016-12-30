package com.marker.biz.test;

import com.marker.biz.domain.Column;
import com.marker.biz.service.GenerateBizService;
import com.marker.biz.service.GenerateDaoService;
import com.marker.biz.service.GenerateEntityService;
import com.marker.biz.service.Table2EntityService;
import com.marker.biz.utils.JDBCUtil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by chenwei23 on 2016/12/15.
 */
public class TestJdbcUtil {

    @Test
    public void test() {
        String jdbcUrl = "";
        String password = "";
        String username = "";


        Table2EntityService service = new Table2EntityService();

        Map map = service.getTablePrimaryKey(jdbcUrl, username, password);
 /*
        GenarateBizService bizService = new GenarateBizService();
        bizService.generateBizInterfaceImpl(map);

        Map map = service.getTable2Entity(jdbcUrl, username, password);

        GenerateEntityService entityService = new GenerateEntityService();
        entityService.generateEntity(map);*/
        GenerateDaoService daoService = new GenerateDaoService();
        daoService.generateBaseDao();
        daoService.generateDao(map);

        System.out.println("gag");

    }


    @Test
    public void testUnsigned() throws SQLException {
        String jdbcUrl = "";
        String password = "";
        String username = "";

        Connection con = JDBCUtil.getConnection(jdbcUrl, username, password);
        List<Column> list = JDBCUtil.getTableColumns(con, null, null, "data_control");
    }
}
