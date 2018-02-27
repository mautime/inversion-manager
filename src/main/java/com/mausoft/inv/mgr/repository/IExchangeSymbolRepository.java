package com.mausoft.inv.mgr.repository;

import org.springframework.stereotype.Repository;

import com.mausoft.common.repository.IBaseRepository;
import com.mausoft.inv.mgr.entity.ExchangeSymbol;

@Repository("exchangeSymbolRepository")
public interface IExchangeSymbolRepository extends IBaseRepository<ExchangeSymbol, Long>{}
