package com.jdbc.tool.test;

import com.jdbc.tool.util.CatalogUtils;


/**  
 * @author chenwei  
 * @date 创建时间：2016年11月13日 下午4:19:54 
 * @version 1.0  
 */

public class catalogContextTest {
	public static void main(String [] args){
		CatalogUtils.setCatalogContext("&", "PLPL");
		String sql = "select * &from mytable where name = 'hah'";
		System.out.println(CatalogUtils.changeCatalog(sql));
	}
}
