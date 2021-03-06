package com.jdbc.tool.domain;
/**  
 * @author chenwei  
 * @date 创建时间：2016年11月10日 下午4:31:55 
 * @version 1.0  可以动态的替换占位符和相对应的字符串
 */

public class CatalogContext {
	private String placeHolder;
	private String catalog;
	
	public CatalogContext(String placeHolder, String catalog) {
		this.placeHolder = placeHolder;
		this.catalog = catalog;
	}
	public String getPlaceHolder() {
		return placeHolder;
	}
	public void setPlaceHolder(String placeHolder) {
		this.placeHolder = placeHolder;
	}
	public String getCatalog() {
		return catalog;
	}
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	@Override
	public String toString() {
		return "CatalogContext [placeHolder=" + placeHolder + ", catalog="
				+ catalog + "]";
	}
	
}
