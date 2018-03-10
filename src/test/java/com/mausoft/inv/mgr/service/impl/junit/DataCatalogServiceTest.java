package com.mausoft.inv.mgr.service.impl.junit;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.mausoft.common.entity.DefaultKeyValueEntity;
import com.mausoft.inv.mgr.entity.ExchangeSymbol;
import com.mausoft.inv.mgr.entity.ExchangeSymbolType;
import com.mausoft.inv.mgr.repository.IExchangeSymbolRepository;
import com.mausoft.inv.mgr.service.IDataCatalogService;
import com.mausoft.inv.mgr.service.impl.DataCatalogService;

@RunWith(SpringRunner.class)
public class DataCatalogServiceTest {
	
	@TestConfiguration
	public static class DataCatalogServiceTestContextConfiguration {
		@Bean
		public IDataCatalogService dataCatalogService() {
			return new DataCatalogService();
		}
	}
	
	private ArrayList<ExchangeSymbol> exchangeSymbols;
	
	@MockBean
	private IExchangeSymbolRepository exchangeSymbolRepository;
	
	@Autowired
	private IDataCatalogService dataCatalogService;
	
	@Before
	public void setUp() {
		exchangeSymbols = new ArrayList<>(0);
		
		exchangeSymbols.add(new ExchangeSymbol("BTC", "Bitoint", new ExchangeSymbolType("CRP", "Cryptocurrency")));
		
		Mockito.when(exchangeSymbolRepository.findAll()).thenReturn(exchangeSymbols);
	}
	
	@Test
	public void whenGetExchangeSymbols() {
		List<DefaultKeyValueEntity> results = null;
		
		results = dataCatalogService.getExchangeSymbols();
		
		assertThat(results).isNotNull().isNotEmpty();
		assertThat(results.get(0)).hasFieldOrPropertyWithValue("name", "BTC").hasFieldOrPropertyWithValue("description", "Bitoint");
	}
}