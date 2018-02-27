package com.mausoft.inv.mgr.controller.junit;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mausoft.common.model.PaginationResult;
import com.mausoft.common.model.PaginationSearch;
import com.mausoft.inv.mgr.controller.ExchangeInversionManagerController;
import com.mausoft.inv.mgr.entity.ExchangeTransaction;
import com.mausoft.inv.mgr.entity.ExchangeTransaction.TransactionType;
import com.mausoft.inv.mgr.service.IExchangeInversionManagerService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ExchangeInversionManagerController.class)
@ContextConfiguration(classes= {ExchangeInversionManagerControllerTest.ApplicationContextConfiguration.class})
public class ExchangeInversionManagerControllerTest {
	
	@SpringBootApplication(scanBasePackageClasses= {ExchangeInversionManagerController.class})
	public static class ApplicationContextConfiguration {
		public ApplicationContextConfiguration() {}
	}
	
	private ExchangeTransaction requestTransaction;
	private ExchangeTransaction responseTransaction;	
	private JacksonTester<ExchangeTransaction> jsonTester;
	
	@MockBean
	private IExchangeInversionManagerService service;
	
	@Autowired
	private MockMvc mvc;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		//mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		
		requestTransaction = new ExchangeTransaction();
		responseTransaction = new ExchangeTransaction();
		
		JacksonTester.initFields(this, new ObjectMapper());
		
		requestTransaction.setSourceAmount(BigDecimal.valueOf(500D));
		requestTransaction.setTransactionFee(BigDecimal.valueOf(7.34D));
		requestTransaction.setExchangeRate(BigDecimal.valueOf(10256.04D));
		requestTransaction.setTargetAmount(BigDecimal.valueOf(0.04803608D));
		
		responseTransaction.setId(1L);
		responseTransaction.setSourceAmount(BigDecimal.valueOf(500D));
		responseTransaction.setTransactionFee(BigDecimal.valueOf(7.34D));
		responseTransaction.setExchangeRate(BigDecimal.valueOf(10256.04D));
		responseTransaction.setTargetAmount(BigDecimal.valueOf(0.04803608D));
		
		BDDMockito.given(service.saveTransaction(BDDMockito.any(ExchangeTransaction.class), BDDMockito.eq(TransactionType.BUY))).willReturn(responseTransaction);
		BDDMockito.given(service.search(any(PaginationSearch.class))).willReturn(new PaginationResult<>(Arrays.asList(responseTransaction), 1));
		BDDMockito.given(service.getTransaction(1)).willReturn(responseTransaction);
	}
	
	@Test
	public void whenSaveBuyTransaction() throws Exception {
		
		mvc.perform(post("/inversion/exchange/transactions").param("type", "BUY")
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content(jsonTester.write(requestTransaction).getJson()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("@.results.id", is(1)));
		
		BDDMockito.verify(service).saveTransaction(BDDMockito.any(ExchangeTransaction.class), BDDMockito.eq(TransactionType.BUY));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void whenSearchTransactions() throws Exception {
		
		mvc.perform(get("/inversion/exchange/transactions").param("max", "10").param("offset", "10")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("@.results.results", Matchers.notNullValue()))
				.andExpect(jsonPath("@.results.results", Matchers.hasSize(1)))
				.andExpect(jsonPath("@.results.results[0].id", is(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("@.results.total", Matchers.equalTo(1)));
		
		BDDMockito.then(service).should().search(BDDMockito.any(PaginationSearch.class));
	}
	
	@Test
	public void whenGetTransaction() throws Exception {
		
		mvc.perform(MockMvcRequestBuilders.get("/inversion/exchange/transactions/{id}", "1")
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.contentType(MediaType.TEXT_PLAIN_VALUE))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("@.results", Matchers.notNullValue()))
		.andExpect(jsonPath("@.results.id", Matchers.equalTo(1)));
		
		Mockito.verify(service).getTransaction(1);
	}
}