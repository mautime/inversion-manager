package com.mausoft.inv.mgr.service;

import java.util.List;

import com.mausoft.common.entity.DefaultKeyValueEntity;

public interface IDataCatalogService {
	public List<DefaultKeyValueEntity> getExchangeSymbols();
}