package com.mausoft.inv.mgr.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mausoft.common.entity.DefaultKeyValueEntity;
import com.mausoft.inv.mgr.repository.IExchangeSymbolRepository;
import com.mausoft.inv.mgr.service.IDataCatalogService;

@Service
public class DataCatalogService implements IDataCatalogService {
	@Autowired
	private IExchangeSymbolRepository exchangeSymbolRepository;
	
	@Override
	public List<DefaultKeyValueEntity> getExchangeSymbols() {
		return Optional.ofNullable(exchangeSymbolRepository.findAll()).orElse(Collections.emptyList())
				.stream().map(e -> new DefaultKeyValueEntity(e.getId(), e.getSymbolCode(), e.getSymbolDescription())).collect(Collectors.toList());
	}
}