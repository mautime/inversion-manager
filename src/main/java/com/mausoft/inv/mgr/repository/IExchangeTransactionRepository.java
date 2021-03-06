package com.mausoft.inv.mgr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mausoft.common.repository.IBaseRepository;
import com.mausoft.inv.mgr.entity.ExchangeTransaction;
import com.mausoft.inv.mgr.entity.projection.ExchangeInversionSummary;

@Repository("exchangeTransactionRepository")
public interface IExchangeTransactionRepository extends IBaseRepository<ExchangeTransaction, Long>, IExchangeTransactionCustomRepository{
	
	public ExchangeTransaction findByIdAndCreatedBy_Id(long id, long createdBy);
	
	@Query(name="getExchangeInversionSummary", nativeQuery=true)
	public List<ExchangeInversionSummary> getExchangeInversionSummary(@Param("createdBy") Long createdBy);
}