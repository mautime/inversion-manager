package com.mausoft.inv.mgr.service.impl.junit;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import com.mausoft.common.model.DefaultSearchCriteria;
import com.mausoft.common.model.PaginationResult;
import com.mausoft.common.model.PaginationSearch;
import com.mausoft.inv.mgr.entity.ExchangeSymbol;
import com.mausoft.inv.mgr.entity.ExchangeTransaction;
import com.mausoft.inv.mgr.entity.ExchangeTransaction.TransactionType;
import com.mausoft.inv.mgr.junit.ApplicationTest;
import com.mausoft.inv.mgr.repository.IExchangeSymbolRepository;
import com.mausoft.inv.mgr.repository.IExchangeTransactionRepository;
import com.mausoft.inv.mgr.service.IExchangeInversionManagerService;
import com.mausoft.inv.mgr.service.impl.ExchangeInversionManagerService;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes= {ExchangeInversionManagerServiceTest.ApplicationContextConfiguration.class, ApplicationTest.class})
public class ExchangeInversionManagerServiceTest {
	private static final long DEFAULT_TOTAL_RESULTS = 100;
	
	@ComponentScan(basePackageClasses= {ExchangeInversionManagerService.class})
	public static class ApplicationContextConfiguration {
		
	}
	
	private BigDecimal buySourceAmount;
	private BigDecimal transactionFee;
	private BigDecimal exchangeRate;
	private BigDecimal sellTargetAmount;
	private ExchangeSymbol bitcoinSymbol;
	private ExchangeTransaction preLoadedTransaction;
	private ExchangeTransaction buyTransaction;
	private ExchangeTransaction sellTransaction;
	private PaginationSearch<DefaultSearchCriteria> paginationSearch;
	
	@Autowired
	private IExchangeTransactionRepository exchangeTransactionRepository;
	
	@Autowired
	private IExchangeSymbolRepository exchangeSymbolRepository;
	
	@Autowired
	private IExchangeInversionManagerService exchangeInversionManagerService;
	
	@Before
	public void setUp() {
		buySourceAmount = new BigDecimal(500D);
		transactionFee = new BigDecimal(7.34D);
		exchangeRate = new BigDecimal(10256.04D);
		sellTargetAmount = new BigDecimal(0.0480360841D);
		buyTransaction = new ExchangeTransaction();
		sellTransaction = new ExchangeTransaction();
		paginationSearch = new PaginationSearch<DefaultSearchCriteria>();
		
		bitcoinSymbol = exchangeSymbolRepository.saveAndFlush(new ExchangeSymbol("BTC", "Bitcoin", null));
		
		buyTransaction.setSourceAmount(buySourceAmount);
		buyTransaction.setTransactionFee(transactionFee);
		buyTransaction.setExchangeRate(exchangeRate);
		buyTransaction.setSymbol(bitcoinSymbol);
		
		sellTransaction.setTransactionFee(transactionFee);
		sellTransaction.setExchangeRate(exchangeRate);
		sellTransaction.setTargetAmount(sellTargetAmount);
		sellTransaction.setSymbol(bitcoinSymbol);
		
		for (int x = 0; x < DEFAULT_TOTAL_RESULTS; x++) {
			ExchangeTransaction exchangeTransaction = new ExchangeTransaction();
			
			exchangeTransaction.setSourceAmount(new BigDecimal(100 + (x + 1)));
			exchangeTransaction.setTransactionFee(new BigDecimal(10D));
			exchangeTransaction.setExchangeRate(new BigDecimal(20D));
			exchangeTransaction.setTargetAmount(exchangeTransaction.getSourceAmount().subtract(exchangeTransaction.getTransactionFee().divide(exchangeTransaction.getExchangeRate(), 5, RoundingMode.HALF_EVEN)));
			exchangeTransaction.setTransactionType(TransactionType.BUY);
			exchangeTransaction.setSymbol(bitcoinSymbol);
			
			preLoadedTransaction = exchangeTransactionRepository.saveAndFlush(exchangeTransaction);
		}
		
		/*Mockito.when(exchangeTransactionRepository.saveAndFlush(buyTransactionWithSourceAmount)).thenReturn(buyTransactionWithSourceAmount);
		Mockito.when(exchangeTransactionRepository.saveAndFlush(buyTransactionWithTargetAmount)).thenReturn(buyTransactionWithTargetAmount);
		Mockito.when(exchangeTransactionRepository.saveAndFlush(sellTransactionWithSourceAmount)).thenReturn(sellTransactionWithSourceAmount);
		Mockito.when(exchangeTransactionRepository.saveAndFlush(sellTransactionWithTargetAmount)).thenReturn(sellTransactionWithTargetAmount);
		Mockito.when(exchangeTransactionRepository.search(paginationSearch)).thenReturn(paginationResult);
		*/
	}
	
	@Test
	public void whenSaveTransactionAsBuy() {
		ExchangeTransaction transaction = null;
		
		transaction = exchangeInversionManagerService.saveTransaction(buyTransaction, TransactionType.BUY);
		
		assertThat(transaction).isNotNull();
		assertThat(transaction.getTargetAmount()).isNotNull()
			.isEqualByComparingTo(BigDecimal.valueOf(0.04803608D));
	}
	
	@Test
	public void whenSaveTransactionAsSell() {
		ExchangeTransaction transaction = null;
		
		transaction = exchangeInversionManagerService.saveTransaction(sellTransaction, TransactionType.SELL);
		
		assertThat(transaction).isNotNull();
		assertThat(transaction.getSourceAmount()).isNotNull().isEqualByComparingTo(new BigDecimal(485.31995792D).setScale(8, RoundingMode.HALF_EVEN));
	}
	
	@Test
	public void whenSaveBuyTransaction() {
		ExchangeTransaction transaction = null;
		
		transaction = exchangeInversionManagerService.saveBuyTransaction(buyTransaction);
		
		assertThat(transaction).isNotNull();
		assertThat(transaction.getTargetAmount()).isNotNull()
			.isEqualByComparingTo(BigDecimal.valueOf(0.04803608D));
	
	}
	
	@Test
	public void whenSaveSellTransaction() {
		ExchangeTransaction transaction = null;
		
		transaction = exchangeInversionManagerService.saveSellTransaction(sellTransaction);
		
		assertThat(transaction).isNotNull();
		assertThat(transaction.getSourceAmount()).isNotNull().isEqualByComparingTo(new BigDecimal(485.31995792D).setScale(8, RoundingMode.HALF_EVEN));
	}
	
	@Test
	public void whenCalculateAmountForBuyTransaction() {
		BigDecimal amount = null;
		
		//amount = exchangeInversionManagerService.calculateAmount(buySourceAmount, transactionFee, exchangeRate, null, TransactionType.BUY);
		
		assertThat(amount).isNotNull().isEqualByComparingTo(BigDecimal.valueOf(0.04803608D));
	}
	
	@Test
	public void whenCalculateAmountForSellTransaction() {
		BigDecimal amount = null;
		
		//amount = exchangeInversionManagerService.calculateAmount(null, transactionFee, exchangeRate, sellTargetAmount, TransactionType.SELL);
		
		assertThat(amount).isNotNull().isEqualByComparingTo(BigDecimal.valueOf(485.31995792D).setScale(8, RoundingMode.HALF_EVEN));
	}
	
	@Test
	public void whenSearchTransaction() {
		PaginationResult<ExchangeTransaction> result = null;
		
		result = exchangeInversionManagerService.search(paginationSearch);
		
		assertThat(result).isNotNull();
		assertThat(result.getResults()).isNotNull().hasSize(PaginationSearch.DEFAULT_MAX);
		assertThat(result.getTotal()).isGreaterThan(PaginationSearch.DEFAULT_MAX).isEqualTo(DEFAULT_TOTAL_RESULTS);
	}
	
	@Test
	public void whenGetTransaction() {
		ExchangeTransaction transaction = null;
		
		transaction = exchangeInversionManagerService.getTransaction(preLoadedTransaction.getId());
		
		assertThat(transaction).isNotNull().hasFieldOrPropertyWithValue("id", preLoadedTransaction.getId());
	}
}