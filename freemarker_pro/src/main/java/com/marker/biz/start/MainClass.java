package com.marker.biz.start;

import com.marker.biz.domain.Attr;
import com.marker.biz.service.*;

import java.util.List;
import java.util.Map;

/**
 * Created by chenwei23 on 2016/12/21.
 */
public class MainClass {

    public static void main(String[] args){
        String jdbcUrl = "";
        String password = "";
        String username = "";

        Table2EntityService service = new Table2EntityService();

        //拿到primaryKey
        Map pmap = service.getTablePrimaryKey(jdbcUrl, username, password);
        Map colMap = service.getTable2Entity(jdbcUrl,username,password);

        //书写comm类，第一步必须生成pom文件。
        GenerateCommService commService = new GenerateCommService();
        commService.generatePom();
        commService.generateComm();

        // 书写配置文件
        GenerateResourceService resourceService = new GenerateResourceService();
        resourceService.generateResource();


        //书写utils类
        GenerateUtilService utilService = new GenerateUtilService();
        utilService.generateUtil();


        //书写实体类
        GenerateEntityService entityService = new GenerateEntityService();
        Map<String, List<Attr>> cMap = service.confirmPrimaryKey(colMap, pmap);
        entityService.generateEntity(cMap);

        //书写service类
        GenerateBizService bizService = new GenerateBizService();
        bizService.generateBizInterface(pmap);
        bizService.generateBizInterfaceImpl(pmap);

      //书写dao类
        GenerateDaoService daoService = new GenerateDaoService();
        daoService.generateBaseDao();
        daoService.generateDao(pmap);

        //书写controller类
        GenerateControllerService controllerService = new GenerateControllerService();
        controllerService.generateController(pmap);


    }
}
