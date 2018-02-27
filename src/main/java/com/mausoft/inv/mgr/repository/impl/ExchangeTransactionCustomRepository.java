package com.mausoft.inv.mgr.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.mausoft.common.model.DefaultSearchCriteria;
import com.mausoft.common.model.PaginationResult;
import com.mausoft.common.model.PaginationSearch;
import com.mausoft.common.util.JpaRepositoryUtils;
import com.mausoft.inv.mgr.entity.ExchangeTransaction;
import com.mausoft.inv.mgr.repository.IExchangeTransactionCustomRepository;
import com.mausoft.inv.mgr.repository.IExchangeTransactionRepository;

@Component("iExchangeTransactionRepositoryImpl")
public class ExchangeTransactionCustomRepository implements IExchangeTransactionCustomRepository {
	
	@Autowired
	private IExchangeTransactionRepository exchangeTransactionRepository;
	
	@Override
	public <T extends DefaultSearchCriteria> PaginationResult<ExchangeTransaction> search(PaginationSearch<T> paginationSearch) {
		Pageable pageRequest = null;
		Page<ExchangeTransaction> pageResults = null;
		PaginationResult<ExchangeTransaction> results = null;
		
		if (paginationSearch != null) {
			pageRequest = JpaRepositoryUtils.buildPageRequest(paginationSearch);
			
			if ((pageResults = exchangeTransactionRepository.findAll(pageRequest)) != null) {
				results = new PaginationResult<>(pageResults.getContent(), pageResults.getTotalElements());
			}
		}
		
		return results;
	}
}