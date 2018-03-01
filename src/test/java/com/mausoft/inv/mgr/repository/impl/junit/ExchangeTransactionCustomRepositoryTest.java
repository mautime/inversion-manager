package com.mausoft.inv.mgr.repository.impl.junit;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mausoft.common.model.DefaultSearchCriteria;
import com.mausoft.common.model.PaginationResult;
import com.mausoft.common.model.PaginationSearch;
import com.mausoft.inv.mgr.entity.ExchangeSymbol;
import com.mausoft.inv.mgr.entity.ExchangeTransaction;
import com.mausoft.inv.mgr.entity.ExchangeTransaction.TransactionType;
import com.mausoft.inv.mgr.junit.ApplicationTest;
import com.mausoft.inv.mgr.repository.IExchangeTransactionRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes=ApplicationTest.class)
public class ExchangeTransactionCustomRepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private IExchangeTransactionRepository exchangeTransactionRepository;
	
	@Before
	public void setUp() {
		ExchangeTransaction exchangeTransaction = null;
		ExchangeSymbol exchangeSymbol = null;
		
		exchangeSymbol = new ExchangeSymbol("BTC", "Bitcoin", null);
		exchangeSymbol = entityManager.persistAndFlush(exchangeSymbol);
		
		for (int x = 0; x < 100; x ++) {
			exchangeTransaction = new ExchangeTransaction();
			
			exchangeTransaction.setSourceAmount(new BigDecimal(100 + (x + 1)));
			exchangeTransaction.setTransactionFee(new BigDecimal(10D));
			exchangeTransaction.setExchangeRate(new BigDecimal(20D));
			exchangeTransaction.setTargetAmount(exchangeTransaction.getSourceAmount().subtract(exchangeTransaction.getTransactionFee().divide(exchangeTransaction.getExchangeRate(), 5, RoundingMode.HALF_EVEN)));
			exchangeTransaction.setSymbol("BTC");
			exchangeTransaction.setTransactionType(TransactionType.BUY);
			
			entityManager.persistAndFlush(exchangeTransaction);
		}
	}
	
	@Test
	public void whenSearchWitoutCriteriaAndSort() {
		final int offset = PaginationSearch.DEFAULT_OFFSET;
		ExchangeTransaction[] paginatedTransactions = null;
		PaginationSearch<DefaultSearchCriteria> pagination = null;
		PaginationResult<ExchangeTransaction> results = null;
		
		pagination = new PaginationSearch<>(PaginationSearch.DEFAULT_MAX, offset);
		
		results = exchangeTransactionRepository.search(pagination);
		
		assertThat(results).isNotNull();
		assertThat(results.getResults()).isNotNull().isNotEmpty().hasSize(PaginationSearch.DEFAULT_MAX);
		
		paginatedTransactions = new ExchangeTransaction[PaginationSearch.DEFAULT_MAX];
		results.getResults().toArray(paginatedTransactions);
		
		assertThat(paginatedTransactions[0]).isNotNull();
		assertThat(paginatedTransactions[0].getSourceAmount()).isEqualByComparingTo(new BigDecimal(100 + offset + 1));
		
		assertThat(paginatedTransactions[PaginationSearch.DEFAULT_MAX - 1]).isNotNull();
		assertThat(paginatedTransactions[PaginationSearch.DEFAULT_MAX - 1].getSourceAmount()).isEqualByComparingTo(new BigDecimal(100 + offset + PaginationSearch.DEFAULT_MAX));
		
		assertThat(results.getTotal()).isEqualTo(100);
	}
}
