package com.mausoft.inv.mgr.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.mausoft.common.model.DefaultSearchCriteria;
import com.mausoft.common.model.KeyValue;
import com.mausoft.common.model.PaginationResult;
import com.mausoft.common.model.PaginationSearch;
import com.mausoft.common.model.PaginationSearch.SortDirection;
import com.mausoft.common.service.ISpringSecurityService;
import com.mausoft.inv.mgr.ExchangeTransactionSearchCriteria;
import com.mausoft.inv.mgr.entity.ExchangeTransaction;
import com.mausoft.inv.mgr.entity.ExchangeTransaction.TransactionType;
import com.mausoft.inv.mgr.entity.projection.ExchangeInversionSummary;
import com.mausoft.inv.mgr.repository.IExchangeTransactionRepository;
import com.mausoft.inv.mgr.service.IExchangeInversionManagerService;

@Service("exchangeInversionManagerService")
public class ExchangeInversionManagerService implements IExchangeInversionManagerService {
	
	@Autowired
	private ISpringSecurityService springSecurityService;
	
	@Autowired
	protected IExchangeTransactionRepository exchangeTransactionRepository;
	
	public ExchangeTransaction getTransaction(long id) {
		return exchangeTransactionRepository.findByIdAndCreatedBy(id, springSecurityService.getCurrentUser());
	}
	
	@Override
	public BigDecimal calculateTargetAmount(BigDecimal sourceAmount, BigDecimal fees, BigDecimal exchangeRate, TransactionType type) {
		BigDecimal result = BigDecimal.ZERO;
		KeyValue<BigDecimal> keyValue = null;
		
		if ((keyValue = _calculateAmount(sourceAmount, fees, exchangeRate, null, type)) != null) {
			result = keyValue.getValue();
		}
		
		return result;
	}
	
	@Override
	public BigDecimal calculateSourceAmount(BigDecimal targetAmount, BigDecimal fees, BigDecimal exchangeRate, TransactionType type) {
		BigDecimal result = BigDecimal.ZERO;
		KeyValue<BigDecimal> keyValue = null;
		
		if ((keyValue = _calculateAmount(null, fees, exchangeRate, targetAmount, type)) != null) {
			result = keyValue.getValue();
		}
		
		return result;
	}
	
	@Override
	public ExchangeTransaction saveBuyTransaction(ExchangeTransaction transaction) {
		return saveTransaction(transaction, TransactionType.BUY);
	}
	
	@Override
	public ExchangeTransaction saveSellTransaction(ExchangeTransaction transaction) {
		return saveTransaction(transaction, TransactionType.SELL);
	}
	
	@Override
	public ExchangeTransaction saveTransaction(ExchangeTransaction transaction, TransactionType transactionType) {
		BigDecimal sourceAmount = null;
		BigDecimal transactionFee = null;
		BigDecimal exchangeRate = null;
		BigDecimal targetAmount = null;
		ExchangeTransaction result = null;
		
		if (transaction != null) {
			sourceAmount = Optional.ofNullable(transaction.getSourceAmount()).orElse(BigDecimal.ZERO);
			transactionFee = Optional.ofNullable(transaction.getTransactionFee()).orElse(BigDecimal.ZERO);
			exchangeRate = Optional.ofNullable(transaction.getExchangeRate()).orElse(BigDecimal.ONE);
			targetAmount = Optional.ofNullable(transaction.getTargetAmount()).orElse(BigDecimal.ZERO);
			
			targetAmount = calculateTargetAmount(sourceAmount, transactionFee, exchangeRate, transactionType);
			
			transaction.setSourceAmount(sourceAmount);
			transaction.setTargetAmount(targetAmount);
			transaction.setTransactionFee(transactionFee);
			transaction.setExchangeRate(exchangeRate);
			transaction.setTransactionType(transactionType);
			
			result = exchangeTransactionRepository.saveAndFlush(transaction);
		}
		
		return result;
	}
	
	public PaginationResult<ExchangeTransaction> search(PaginationSearch<ExchangeTransactionSearchCriteria> paginationSearch){
		paginationSearch.getSearchCriteria().setCreatedBy(springSecurityService.getCurrentUser());
		
		if (paginationSearch != null) {
			paginationSearch.setSort(StringUtils.isNotBlank(paginationSearch.getSort()) ? paginationSearch.getSort() : "transactionDate");
			paginationSearch.setDir(paginationSearch.getDir() != null ? paginationSearch.getDir() : SortDirection.DESC);
		}
		return exchangeTransactionRepository.search(paginationSearch);
	}
	
	@Override
	public BigDecimal getInversionTotal() {
		BigDecimal total = null;
		List<ExchangeInversionSummary> inversionsSummary = null;
		
		if (!CollectionUtils.isEmpty(inversionsSummary = getInversionSummary())) {
			total = BigDecimal.ZERO;
			
			for (ExchangeInversionSummary summary : inversionsSummary) {
				total = total.add(summary.getTotalSourceAmount());
			}
		}
		
		return total;
	}
	
	@Override
	public List<ExchangeInversionSummary> getInversionSummary(){
		BigDecimal totalSourceAmount = null;
		BigDecimal totalTargetAmount = null;
		List<String> exchangeSymbols = null;
		List<ExchangeInversionSummary> transactionsSummary = null;
		List<ExchangeInversionSummary> results = null;
		
		if (!CollectionUtils.isEmpty(transactionsSummary = exchangeTransactionRepository.getExchangeInversionSummary(springSecurityService.getCurrentUser()))) {
			exchangeSymbols = transactionsSummary.stream().map(e -> e.getExchangeSymbol()).distinct().collect(Collectors.toList());
			results = new ArrayList<>(exchangeSymbols.size());
			
			for (String exchangeSymbol : exchangeSymbols) {
				totalSourceAmount = BigDecimal.ZERO;
				totalTargetAmount = BigDecimal.ZERO;
				
				for (ExchangeInversionSummary transactionSummary : transactionsSummary) {
					
					if (StringUtils.isNotBlank(transactionSummary.getExchangeSymbol()) && transactionSummary.getExchangeSymbol().equalsIgnoreCase(exchangeSymbol) && 
							StringUtils.isNotBlank(transactionSummary.getTransactionType())) {
						
						if (transactionSummary.getTransactionType().equalsIgnoreCase(TransactionType.BUY.name())) {
							totalSourceAmount = totalSourceAmount.add(transactionSummary.getTotalSourceAmount());
							totalTargetAmount = totalTargetAmount.add(transactionSummary.getTotalTargetAmount());
						} else if (transactionSummary.getTransactionType().equalsIgnoreCase(TransactionType.SELL.name())) {
							totalSourceAmount = totalSourceAmount.subtract(transactionSummary.getTotalSourceAmount());
							totalTargetAmount = totalTargetAmount.subtract(transactionSummary.getTotalTargetAmount());
						}
					}
				}
				
				results.add(new ExchangeInversionSummary(exchangeSymbol, null, totalSourceAmount, totalTargetAmount));
			}
		}
		
		return results;
	}
	
	public void deleteTransaction(Long id) {
		exchangeTransactionRepository.delete(id);
	}
	
	private KeyValue<BigDecimal> _calculateAmount(BigDecimal sourceAmount, BigDecimal fees, BigDecimal exchangeRate, BigDecimal targetAmount, TransactionType type) {
		BigDecimal amt = null;
		BigDecimal fee = null;
		BigDecimal rate = null;
		KeyValue<BigDecimal> calculatedAmount = null;
		
		if (type != null) {
			fee = Optional.ofNullable(fees).orElse(BigDecimal.ZERO);
			rate = Optional.ofNullable(exchangeRate).orElse(BigDecimal.ONE);
			
			switch(type) {
				case BUY:
					
					if (sourceAmount != null) {
						amt = Optional.ofNullable(sourceAmount).orElse(BigDecimal.ZERO);
						calculatedAmount = new KeyValue<>("targetAmount", amt.subtract(fee).divide(rate, 8, RoundingMode.HALF_EVEN));
					} else if (targetAmount != null) {
						amt = Optional.ofNullable(targetAmount).orElse(BigDecimal.ZERO);
						calculatedAmount = new KeyValue<>("sourceAmount", amt.setScale(8, RoundingMode.HALF_EVEN).multiply(rate).add(fee).setScale(2, RoundingMode.HALF_EVEN));
					}
					
				break;
				case SELL:
					
					if (targetAmount != null) {
						amt = Optional.ofNullable(targetAmount).orElse(BigDecimal.ZERO);
						calculatedAmount = new KeyValue<>("sourceAmount", amt.setScale(8, RoundingMode.HALF_EVEN).multiply(rate).subtract(fee).setScale(2, RoundingMode.HALF_EVEN));
					} else if (sourceAmount != null) {
						amt = Optional.ofNullable(sourceAmount).orElse(BigDecimal.ZERO);
						calculatedAmount = new KeyValue<>("targetAmount", amt.subtract(fee).divide(rate, 8, RoundingMode.HALF_EVEN));
					}
					
				break;
			}
		}
		
		return calculatedAmount;
	}

}