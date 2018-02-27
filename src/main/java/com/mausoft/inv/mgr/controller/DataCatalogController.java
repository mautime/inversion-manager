package com.mausoft.inv.mgr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mausoft.common.model.IAjaxResponse;
import com.mausoft.common.model.SuccessAjaxResponse;
import com.mausoft.inv.mgr.service.IDataCatalogService;

@RestController
@RequestMapping("/data")
public class DataCatalogController {
	
	@Autowired
	private IDataCatalogService dataCatalogService;
	
	@GetMapping(path="/exchange/symbols")
	public ResponseEntity<IAjaxResponse> getExchangeSymbols(){
		IAjaxResponse ajaxResponse = null;
		
		ajaxResponse = new SuccessAjaxResponse();
		
		ajaxResponse.addProperty("results", dataCatalogService.getExchangeSymbols());
		
		return ResponseEntity.ok(ajaxResponse);
	}
}
