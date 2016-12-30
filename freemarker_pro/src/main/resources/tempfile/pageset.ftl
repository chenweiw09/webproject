package ${packageName};

import java.io.Serializable;

/**
* Created by ${author} on ${date}.
*/
public class PageSet<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
    * 当前页
    */
    private int currPage = 1;
    /**
    * 总页数
    */
    private int totalPage = 1;
    /**
    * 查询 起始位数
    */
    private int offset;
    /**
    * 查询长度
    */
    private int pageSize = 0;
    /**
    * 数据总数
    */
    private int totalCount = 0;
    /**
    * 是否存在下一页
    */
    private boolean hasNextPage = false;
    /**
    * 是否存在上一页
    */
    private boolean hasPrePage = false;

    /**
    * 下一页
    */
    private Integer nextPage = null;
    /**
    * 上一页
    */
    private Integer prePage = null;
    /**
    * 列表数据
    */
    private T data;


    public PageSet() {}

    public PageSet(int currPage, int pageSize, int totalCount, T data){
    this.currPage = currPage;
    this.pageSize = pageSize;
    this.totalCount = totalCount;
    this.data = data;

    init();
    }

    public void init(){
    totalPage = (int)Math.ceil(totalCount/(double)pageSize);
    offset = (currPage-1)*pageSize;
    if(currPage <= 1){
    hasPrePage = false;
    if(currPage >= totalPage){
    hasNextPage = false;
    }else{
    hasNextPage = true;
    nextPage = currPage + 1;
    }
    }else if(currPage >1){
    hasPrePage = true;
    prePage = currPage - 1;
    if(currPage<totalPage){
    hasNextPage = true;
    nextPage = currPage + 1;
    }else{
    hasNextPage = false;
    }
    }
    }

    public int getCurrPage() {
    return currPage;
    }

    public void setCurrPage(int currPage) {
    this.currPage = currPage;
    }

    public T getData() {
    return data;
    }

    public void setData(T data) {
    this.data = data;
    }

    public int getTotalCount() {
    return totalCount;
    }

    public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
    }

    public int getPageSize() {
    return pageSize;
    }

    public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
    }

    public int getOffset() {
    return offset;
    }

    public boolean isHasNextPage() {
    return hasNextPage;
    }

    public boolean isHasPrePage() {
    return hasPrePage;
    }

    public Integer getNextPage() {
    return nextPage;
    }

    public Integer getPrePage() {
    return prePage;
    }

    public int getTotalPage() {
    return totalPage;
    }
    }