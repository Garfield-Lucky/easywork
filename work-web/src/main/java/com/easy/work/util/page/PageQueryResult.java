package com.easy.work.util.page;

import java.util.List;

/**
 * @className:Page.java
 * @classDescription:分页参数对象
 * @author:linjiekai
 * @createTime:2010-7-12
 */
public class PageQueryResult<T> {
	private PageSettings pageSetting;
	//-- 分页参数 --//
	protected int pageNo = 1;// 页数
	protected int pageSize = 20;// 显示条数
	protected int leftCount=3;// 左边显示的条数
	protected int rigthCount=3;// 右边显示的条数
	private String forwordName;// 跳转页面

	protected List<T> result;//取得页内的记录列表.

	//-- 返回结果 --//
	protected long totalCount = -1;// 总条数

	protected int pageMaxNo;//总共有几页
	
	public PageQueryResult(PageSettings pageSetting) {
		this.pageSetting=pageSetting;
		this.pageNo=pageSetting.getPageNo();
		this.pageSize=pageSetting.getPageSize();
	}


	//-- 访问查询参数函数 --//
	
	
    /**
     * 取得最大页码数
     * @param size
     * @param totalRows
     * @return
     */
    public int getPageMaxNo(int size ,int totalRows) {   
        // 最大页数   
        int pageMaxNo;   
        // 实际每页数据条数   
        int actualSize;   
        // 总记录数   
        //int totalRows = this.getTotalRows();   
        // 计算实际每页的条数,如果请求的每页数据条数大于总条数, 则等于总条数   
        actualSize = (size > totalRows) ? totalRows : size;   
        if (totalRows > 0) {   
        	pageMaxNo = (totalRows % size == 0) ? (totalRows / actualSize)   
                    : (totalRows / actualSize + 1);   
        } else {   
        	pageMaxNo = 0;   
        }   
        this.pageMaxNo=pageMaxNo;
        return pageMaxNo;   
    } 
	
	
	/**
	 * 获得当前页的页号,序号如果大于总条数，则当前页定位到总页数
	 */
	public int getPageNo() {
		if(this.getTotalPages()>-1&&this.pageNo>this.getTotalPages()){		
			this.pageNo=(int) this.getTotalPages();
		}
		return pageNo;
	}

	/**
	 * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
	 */
	public void setPageNo(final int pageNo) {
		this.pageNo = pageNo;

		if (pageNo < 1) {
			this.pageNo = 1;
		}
		
	}

	public PageQueryResult<T> pageNo(final int thePageNo) {
		setPageNo(thePageNo);
		return this;
	}

	/**
	 * 获得每页的记录数量,默认为1.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页的记录数量,低于1时自动调整为1.
	 */
	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;

		if (pageSize < 1) {
			this.pageSize = 1;
		}
	}

	public PageQueryResult<T> pageSize(final int thePageSize) {
		setPageSize(thePageSize);
		return this;
	}

	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
	 */
	public int getFirst() {
		return ((pageNo - 1) * pageSize) + 1;
	}


	//-- 访问查询结果函数 --//

	/**
	 * 取得页内的记录列表.
	 */
	public List<T> getResult() {
		return result;
	}

	/**
	 * 设置页内的记录列表.
	 */
	public void setResult(final List<T> result) {
		this.result = result;
	}

	/**
	 * 取得总记录数, 默认值为-1.
	 */
	public long getTotalCount() {
		return totalCount;
	}

	/**
	 * 设置总记录数.
	 */
	public void setTotalCount(final long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 根据pageSize与totalCount计算总页数, 默认值为-1.
	 */
	public long getTotalPages() {
		if (totalCount < 0) {
			return -1;
		}

		long count = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			count++;
		}
		return count;
	}

	/**
	 * 是否还有下一页.
	 */
	public boolean isHasNext() {
		return (pageNo + 1 <= getTotalPages());
	}

	/**
	 * 取得下页的页号, 序号从1开始.
	 * 当前页为尾页时仍返回尾页序号.
	 */
	public int getNextPage() {
		if (isHasNext()) {
			return pageNo + 1;
		} else {
			return pageNo;
		}
	}

	/**
	 * 是否还有上一页.
	 */
	public boolean isHasPre() {
		return (pageNo - 1 >= 1);
	}

	/**
	 * 取得上页的页号, 序号从1开始.
	 * 当前页为首页时返回首页序号.
	 */
	public int getPrePage() {
		if (isHasPre()) {
			return pageNo - 1;
		} else {
			return pageNo;
		}
	}

	/**
	 * @return the leftCount
	 */
	public int getLeftCount() {
		return leftCount;
	}

	/**
	 * @param leftCount the leftCount to set
	 */
	public void setLeftCount(int leftCount) {
		this.leftCount = leftCount;
	}

	/**
	 * @return the rigthCount
	 */
	public int getRigthCount() {
		return rigthCount;
	}

	/**
	 * @param rigthCount the rigthCount to set
	 */
	public void setRigthCount(int rigthCount) {
		this.rigthCount = rigthCount;
	}

	/**
	 * @return the forwordName
	 */
	public String getForwordName() {
		return forwordName;
	}

	/**
	 * @param forwordName the forwordName to set
	 */
	public void setForwordName(String forwordName) {
		this.forwordName = forwordName;
	}


	public int getPageMaxNo() {
        // 最大页数   
        long pageMaxNo;   
 
        if (totalCount > 0) {   
        	pageMaxNo = (totalCount % pageSize == 0) ? (totalCount / pageSize)   
                    : (totalCount / pageSize + 1);   
        } else {   
        	pageMaxNo = 0;   
        }   
		
		
		return Long.valueOf(pageMaxNo).intValue();
	}


	public void setPageMaxNo(int pageMaxNo) {
		this.pageMaxNo = pageMaxNo;
	}
	
	
}

