package com.easy.work.util.page;


import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author linjiekai
 * 2011-7-12 上午11:18:29 PageSettings.java
 */
public class PageSettings implements Serializable {

	private static final long serialVersionUID = 5851054496950476L;

	private Integer pageNo = 1;
	private Integer pageSize = 20;
	protected String order = null;//设置排序字段，多个字段用，隔开 格式（name:desc,createDate:asc）
	private String sortName;
	private String tableAsName;//表别名
	
	public static PageSettings of(Integer pageNo) {
		return of(pageNo, 20);
	}

	public static PageSettings of(Integer pageNo, Integer pageSize) {
		PageSettings settings = new PageSettings();
		if(null!=pageNo)
		settings.setPageNo(pageNo);
		if(null!=pageSize)
		settings.setPageSize(pageSize);
		return settings;
	}
	public static PageSettings of(Integer pageNo, Integer pageSize, String sortName, String sortOrder){
		PageSettings settings = new PageSettings();
		if(null!=pageNo)
		settings.setPageNo(pageNo);
		if(null!=pageSize)
		settings.setPageSize(pageSize);
		if(sortName!=null &sortOrder!=null){//排序
			settings.setSortName(sortName);
			settings.setOrder(sortOrder);
		}
		return settings;
	}

	public Integer getPageNo() {
		return pageNo;
	}
	
	/**
	 * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
	 */
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;

		if (pageNo < 1) {
			this.pageNo = 1;
		}
		
	}
	public Integer getPageSize() {
		return pageSize;
	}
	/**
	 * 设置每页的记录数量,低于1时自动调整为1.
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;

		if (pageSize < 1) {
			this.pageSize = 1;
		}
	}

	public void apply(PageSettings settings) {
		if(settings.getPageNo() != null) {
			this.pageNo = settings.getPageNo();
		}
		if(settings.getPageSize() != null) {
			this.pageSize = settings.getPageSize();
		}
	}
	
	/**
	 * 获得排序方向.
	 */
	public String getOrder() {
		StringBuffer orderBy=new StringBuffer(" order by ");
		if(null!=this.getTableAsName())
			orderBy.append(this.getTableAsName()+".");	
		orderBy.append(getSortName()+" "+getAscDesc());
		return orderBy.toString();
	}

	public String getAscDesc(){
		return this.order;
	}
	
	/**
	 * 设置排序方式向.
	 * 
	 * @param order 可选值为desc或asc,多个排序字段时用','分隔.
	 */
	public void setOrder(String order) {
		this.order = order;
	}
	

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getTableAsName() {
		return tableAsName;
	}

	public void setTableAsName(String tableAsName) {
		this.tableAsName = tableAsName;
	}

	/**
	 * 是否已设置排序字段,无默认值.
	 */
	public boolean isOrderBySetted() {
		return StringUtils.isNotBlank(order);
	}
}
