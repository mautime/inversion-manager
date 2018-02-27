package com.mausoft.inv.mgr.service;

import java.math.BigDecimal;
import java.util.List;

import com.mausoft.common.model.DefaultSearchCriteria;
import com.mausoft.common.model.KeyValue;
import com.mausoft.common.model.PaginationResult;
import com.mausoft.common.model.PaginationSearch;
import com.mausoft.inv.mgr.entity.ExchangeTransaction;
import com.mausoft.inv.mgr.entity.ExchangeTransaction.TransactionType;
import com.mausoft.inv.mgr.entity.projection.ExchangeInversionSummary;

public interface IExchangeInversionManagerService {
	public KeyValue<BigDecimal> calculateAmount(BigDecimal sourceAmount, BigDecimal fees, BigDecimal exchangeRate, BigDecimal targetAmount, TransactionType transactionType);
	public ExchangeTransaction getTransaction(long id);
	public ExchangeTransaction saveTransaction(ExchangeTransaction transaction, TransactionType transactionType);
	public ExchangeTransaction saveBuyTransaction(ExchangeTransaction transaction);
	public ExchangeTransaction saveSellTransaction(ExchangeTransaction transaction);
	public <T extends DefaultSearchCriteria> PaginationResult<ExchangeTransaction> search(PaginationSearch<T> paginationSearch);
	public List<ExchangeInversionSummary> getInversionSummary();
	public BigDecimal getInversionTotal();
}