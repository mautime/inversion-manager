package com.mausoft.inv.mgr;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.mausoft.common.model.DefaultSearchCriteria;

public class ExchangeTransactionSearchCriteria extends DefaultSearchCriteria {
	private static final long serialVersionUID = -1936355106563016844L;
	
	@DateTimeFormat(pattern="MM-dd-yyyy")
	private Date transactionDateFrom;
	@DateTimeFormat(pattern="MM-dd-yyyy")
	private Date transactionDateTo;
	private List<String> symbols;
	public Date getTransactionDateFrom() {
		return transactionDateFrom;
	}
	public void setTransactionDateFrom(Date aTransactionDateFrom) {
		transactionDateFrom = aTransactionDateFrom;
	}
	public Date getTransactionDateTo() {
		return transactionDateTo;
	}
	public void setTransactionDateTo(Date aTransactionDateTo) {
		transactionDateTo = aTransactionDateTo;
	}
	public List<String> getSymbols() {
		return symbols;
	}
	public void setSymbols(List<String> aSymbols) {
		symbols = aSymbols;
	}
}