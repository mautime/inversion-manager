package com.mausoft.inv.mgr.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mausoft.common.model.DefaultSearchCriteria;
import com.mausoft.common.model.IAjaxResponse;
import com.mausoft.common.model.PaginationSearch;
import com.mausoft.common.model.SuccessAjaxResponse;
import com.mausoft.inv.mgr.entity.ExchangeTransaction;
import com.mausoft.inv.mgr.entity.ExchangeTransaction.TransactionType;
import com.mausoft.inv.mgr.service.IExchangeInversionManagerService;

@RestController
@RequestMapping(path="/inversion/exchange", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExchangeInversionManagerController {
	
	@Autowired
	private IExchangeInversionManagerService exchangeInversionManagerService;
	
	@GetMapping(path="transactions/{id}")
	public ResponseEntity<IAjaxResponse> getTransaction(@PathVariable long id){
		IAjaxResponse ajaxResponse = null;
		
		ajaxResponse = new SuccessAjaxResponse();
		
		ajaxResponse.addProperty("results", exchangeInversionManagerService.getTransaction(id));
		
		return ResponseEntity.ok(ajaxResponse);
	}
	
	@GetMapping(path="/transactions")
	public ResponseEntity<IAjaxResponse> searchTransactions(@RequestParam int max, @RequestParam int offset, @ModelAttribute DefaultSearchCriteria searchCriteria){
		PaginationSearch<DefaultSearchCriteria> paginationSearch = null;
		IAjaxResponse ajaxResponse = null;
		
		paginationSearch = new PaginationSearch<>(max, offset);
		ajaxResponse = new SuccessAjaxResponse();
		
		paginationSearch.setSearchCriteria(searchCriteria);
		
		ajaxResponse.addProperty("results", exchangeInversionManagerService.search(paginationSearch));
		
		return ResponseEntity.ok(ajaxResponse);
	}
	
	@PostMapping(path="/transactions", consumes= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<IAjaxResponse> saveTransaction(@RequestBody ExchangeTransaction transaction, @RequestParam String type) {
		IAjaxResponse ajaxResponse = null;
		
		ajaxResponse = new SuccessAjaxResponse();
		
		ajaxResponse.addProperty("results", exchangeInversionManagerService.saveTransaction(transaction, TransactionType.valueOf(type)));
		
		return ResponseEntity.ok(ajaxResponse);
	}
	
	@GetMapping(path="/transactions/helper/calculateAmount")
	public ResponseEntity<IAjaxResponse> calculateAmount(@RequestParam(name="sourceAmount", required=false) BigDecimal sourceAmount, @RequestParam("transactionFee") BigDecimal fee, @RequestParam("exchangeRate") BigDecimal exchangeRate, @RequestParam(name="targetAmount", required=false) BigDecimal targetAmount, @RequestParam String transactionType){
		IAjaxResponse ajaxResponse = null;
		
		ajaxResponse = new SuccessAjaxResponse("results", exchangeInversionManagerService.calculateAmount(sourceAmount, fee, exchangeRate, targetAmount, TransactionType.valueOf(transactionType)));
		
		return ResponseEntity.ok(ajaxResponse);
	}
	
	@GetMapping(path="/summary")
	public ResponseEntity<IAjaxResponse> getInversionSummary(){
		IAjaxResponse ajaxResponse = null;
		Map<String, Object> resultsMap = null;
		
		resultsMap = new HashMap<>(2);
		
		resultsMap.put("summary", exchangeInversionManagerService.getInversionSummary());
		resultsMap.put("total", exchangeInversionManagerService.getInversionTotal());
		
		ajaxResponse = new SuccessAjaxResponse("results", resultsMap);
		
		return ResponseEntity.ok(ajaxResponse);
	}
}