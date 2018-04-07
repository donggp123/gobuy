package com.cndinuo.page;

import java.util.List;
import java.util.Map;

/**
 * 分页参数对象
 * @author cndinuo
 */
public class Page<T> implements java.io.Serializable{

	private int pageSize; 				// 每页记录数
	private int currentPage; 			// 当前页
	private int pageCount; 				// 总页数
	private int totalCount; 			// 总记录数
	private int defaultPageSize = 15; 	// 缺省每页记录数
	private int maxPageCount = 999; 	// 缺省最大页数

	private List<T> results;			//对应的当前页记录
    private Map<String,Object> p;						//其他的参数我们把它分装成一个Map对象
	
	public Page() {
		pageSize = defaultPageSize;
		currentPage = 1;
		pageCount = 0;
		totalCount = 0;
	}

	public void setPageSize(int pageSize) {
		if (pageSize <= 0) {
			pageSize = defaultPageSize;
		}
		this.pageSize = pageSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setCurrentPage(int currentPage) {
		if (currentPage <= 0) {
			currentPage = 1;
		}
		this.currentPage = currentPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getPageCount() {
		if (pageCount == 0) {
			this.pageCount = (totalCount - 1) / pageSize + 1;

			if (currentPage > pageCount) {
				this.currentPage = pageCount;
			}
		}

		if (pageCount > maxPageCount) {
			pageCount = maxPageCount;
		}

		return pageCount;
	}


	public void setTotalCount(int totalCount) {
		if (totalCount <= 0) {
			totalCount = 0;
		}
		this.totalCount = totalCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setDefaultPageSize(int defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}

	public int getDefaultPageSize() {
		return defaultPageSize;
	}

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		getPageCount();
		this.results = results;
	}

	public Map<String, Object> getP() {
		return p;
	}

	public void setP(Map<String,Object> p) {
		this.p = p;
	}
}
