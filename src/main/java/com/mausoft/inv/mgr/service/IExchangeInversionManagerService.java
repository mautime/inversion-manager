package com.mausoft.inv.mgr.service;

import java.math.BigDecimal;
import java.util.List;

import com.mausoft.common.model.PaginationResult;
import com.mausoft.common.model.PaginationSearch;
import com.mausoft.inv.mgr.entity.ExchangeTransaction;
import com.mausoft.inv.mgr.entity.ExchangeTransaction.TransactionType;
import com.mausoft.inv.mgr.entity.projection.ExchangeInversionSummary;
import com.mausoft.inv.mgr.model.ExchangeTransactionSearchCriteria;

public interface IExchangeInversionManagerService {
	public BigDecimal calculateSourceAmount(BigDecimal targetAmount, BigDecimal fees, BigDecimal exchangeRate, TransactionType transactionType);
	public BigDecimal calculateTargetAmount(BigDecimal source, BigDecimal fees, BigDecimal exchangeRate, TransactionType transactionType);
	public ExchangeTransaction getTransaction(long id);
	public ExchangeTransaction saveTransaction(ExchangeTransaction transaction, TransactionType transactionType);
	public ExchangeTransaction saveBuyTransaction(ExchangeTransaction transaction);
	public ExchangeTransaction saveSellTransaction(ExchangeTransaction transaction);
	public PaginationResult<ExchangeTransaction> search(PaginationSearch<ExchangeTransactionSearchCriteria> paginationSearch);
	public List<ExchangeInversionSummary> getInversionSummary();
	public BigDecimal getInversionTotal();
	public void deleteTransaction(Long id);
}