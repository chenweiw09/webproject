package com.jdbc.tool.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**  
 * @author chenwei  
 * @date 创建时间：2016年11月4日 下午2:06:02 
 * @version 1.0  默认的分页设置
 */

public class PageSet<T> implements Serializable{
	private static final long serialVersionUID = 1L;
	private int pageNum;
	private int pageSize = 10;
	private int totalCount;
	private int curCount;
	private int totalPage;
	private List<T> list = new ArrayList<T>();
	
	public PageSet(){}
	
	public PageSet(int pageNum, int pageSize, int totoalCount, List<T> list){
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.list = list;
		if(list != null){
			this.curCount = list.size();
		}
		
		this.totalPage=totalCount/pageSize+1;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getCurCount() {
		return curCount;
	}

	public void setCurCount(int curCount) {
		this.curCount = curCount;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	
	
}
