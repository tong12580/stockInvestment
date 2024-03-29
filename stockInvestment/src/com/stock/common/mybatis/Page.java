package com.stock.common.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Page<T> {
	
	private int pageNo = 1;//页码，默认是第一页
    private int pageSize = 15;//每页显示的记录数，默认是15
    private int totalRecord;//总记录数
    private int totalPage;//总页数
    private List<?> results;//对应的当前页记录
    private Map<String, Object> params = new HashMap<String, Object>();//其他的参数我们把它分装成一个Map对象
    
    /** 页码*/
    public int getPageNo() {
       return pageNo;
    }
    
    public void setPageNo(int pageNo) {
       this.pageNo = pageNo;
    }
    
    /** 每页记录数*/
    public int getPageSize() {
       return pageSize;
    }
 
    public void setPageSize(int pageSize) {
       this.pageSize = pageSize;
    }
    
    /** 总页数*/
    public int getTotalRecord() {
       return totalRecord;
    }
 
    public void setTotalRecord(int totalRecord) {
    	
       this.totalRecord = totalRecord;
       //在设置总页数的时候计算出对应的总页数，在下面的三目运算中加法拥有更高的优先级，所以最后可以不加括号。
       int totalPage = totalRecord%pageSize==0 ? totalRecord/pageSize : totalRecord/pageSize + 1;
       this.setTotalPage(totalPage);
       
       if(0!=totalRecord)
    	   params.clear();
    }
 
    public int getTotalPage() {
       return totalPage;
    }
 
    public void setTotalPage(int totalPage) {
       this.totalPage = totalPage;
    }
 
    public List<?> getResults() {
       return results;
    }
 
    public void setResults(List<?> results) {
       this.results = results;
    }
   
    public Map<String, Object> getParams() {
       return params;
    }
   
    public void setParams(Map<String, Object> params) {
       this.params = params;
    }
 
    @Override
    public String toString() {
       StringBuilder builder = new StringBuilder();
       builder.append("Page [pageNo=").append(pageNo).append(", pageSize=")
              .append(pageSize).append(", results=").append(results).append(
                     ", totalPage=").append(totalPage).append(
                     ", totalRecord=").append(totalRecord).append("]");
       return builder.toString();
    }
}