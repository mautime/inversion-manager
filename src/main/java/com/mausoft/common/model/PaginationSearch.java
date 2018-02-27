package com.mausoft.common.model;

import java.io.Serializable;

public class PaginationSearch<T extends Serializable> implements IModel {	
	private static final long serialVersionUID = 7970451298750141758L;
	public static final int DEFAULT_MAX = 10;
	public static final int DEFAULT_OFFSET = 0;
	
	public enum SortDirection {
		ASC, DESC;
	}
	
	private int max;
	private int offset;
	private String sort;
	private SortDirection dir;
	private T searchCriteria;
	
	public PaginationSearch(int aMax, int aOffset, String aSort, SortDirection aDir, T aSearchCriteria) {
		max = aMax;
		offset = aOffset;
		sort = aSort;
		dir = aDir;
		searchCriteria = aSearchCriteria;
	}
	
	public PaginationSearch(int aMax, int aOffset, String aSort, SortDirection aDir) {
		this(aMax, aOffset, aSort, aDir, null);
	}
	
	public PaginationSearch(int aMax, int aOffset) {
		this(aMax, aOffset, null, null);
	}
	
	public PaginationSearch() {
		this(DEFAULT_MAX, DEFAULT_OFFSET);
	}
	
	public int getMax() {
		return max;
	}
	public void setMax(int aMax) {
		max = aMax;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int aOffset) {
		offset = aOffset;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String aSort) {
		sort = aSort;
	}
	public SortDirection getDir() {
		return dir;
	}

	public void setDir(SortDirection aDir) {
		dir = aDir;
	}
	public T getSearchCriteria() {
		return searchCriteria;
	}
	public void setSearchCriteria(T aSearchCriteria) {
		searchCriteria = aSearchCriteria;
	}
}