package com.mausoft.common.model;

import java.io.Serializable;
import java.util.Collection;

public class PaginationResult<T extends Serializable> implements IModel {
	private static final long serialVersionUID = 951258233919260207L;
	
	private long total;
	private Collection<T> results;
	
	public PaginationResult(Collection<T> aResults, long aTotal) {
		results = aResults;
		total = aTotal;
	}
	
	public long getTotal() {
		return total;
	}
	public void setTotal(long aTotal) {
		total = aTotal;
	}
	public Collection<T> getResults() {
		return results;
	}
	public void setResults(Collection<T> aResults) {
		results = aResults;
	}
}
