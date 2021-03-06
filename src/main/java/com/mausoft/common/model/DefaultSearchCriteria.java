package com.mausoft.common.model;

public class DefaultSearchCriteria implements IModel {
	private static final long serialVersionUID = -5382681585539765382L;
	
	private String query;
	private Long createdBy;
	
	public DefaultSearchCriteria() {}
	
	public DefaultSearchCriteria(String aQuery) {
		query = aQuery;
	}
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String aQuery) {
		query = aQuery;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long aCreatedBy) {
		createdBy = aCreatedBy;
	}
}