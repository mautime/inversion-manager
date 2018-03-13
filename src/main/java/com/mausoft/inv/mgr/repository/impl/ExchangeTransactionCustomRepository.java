package com.mausoft.inv.mgr.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.mausoft.common.model.DefaultSearchCriteria;
import com.mausoft.common.model.PaginationResult;
import com.mausoft.common.model.PaginationSearch;
import com.mausoft.common.util.JpaRepositoryUtils;
import com.mausoft.common.util.SpecificationsBuilder;
import com.mausoft.inv.mgr.ExchangeTransactionSearchCriteria;
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
			
			if ((pageResults = exchangeTransactionRepository.findAll(_processExchangeTransactionSearchCriteria((ExchangeTransactionSearchCriteria) paginationSearch.getSearchCriteria()), pageRequest)) != null) {
				results = new PaginationResult<>(pageResults.getContent(), pageResults.getTotalElements());
			}
		}
		
		return results;
	}
	
	@SuppressWarnings("unchecked")
	private Specification<ExchangeTransaction> _processExchangeTransactionSearchCriteria(ExchangeTransactionSearchCriteria searchCriteria){
		Specifications<ExchangeTransaction> specCriteria = null;
		
		if (searchCriteria != null) {
			specCriteria = Specifications.where(SpecificationsBuilder.equal(1, 1));
			
			if (!CollectionUtils.isEmpty(searchCriteria.getSymbols())) {
				specCriteria = specCriteria.and(SpecificationsBuilder.in("symbol", searchCriteria.getSymbols(), ExchangeTransaction.class));
			}
			
			if (searchCriteria.getTransactionDateFrom() != null) {
				specCriteria = specCriteria.and(SpecificationsBuilder.gte("transactionDate", searchCriteria.getTransactionDateFrom(), ExchangeTransaction.class));
			}
			
			if (searchCriteria.getTransactionDateTo() != null) {
				specCriteria = specCriteria.and(SpecificationsBuilder.lte("transactionDate", searchCriteria.getTransactionDateTo(), ExchangeTransaction.class));
			}
			
			if (StringUtils.isNotBlank(searchCriteria.getCreatedBy())) {
				specCriteria = specCriteria.and(SpecificationsBuilder.like("createdBy.email", searchCriteria.getCreatedBy(), ExchangeTransaction.class));
			}
		}
		
		return specCriteria;
	}
}