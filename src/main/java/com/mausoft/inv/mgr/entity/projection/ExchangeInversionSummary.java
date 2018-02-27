package com.mausoft.inv.mgr.entity.projection;

import java.math.BigDecimal;

import com.mausoft.common.entity.IEntity;

public class ExchangeInversionSummary implements IEntity {
	private static final long serialVersionUID = 8073764345745941817L;
	
	private String exchangeSymbol;
	private String transactionType;
	private BigDecimal totalSourceAmount;
	private BigDecimal totalTargetAmount;
	
	public ExchangeInversionSummary() {}
	
	public ExchangeInversionSummary(String aExchangeSymbol, String aTransactionType, BigDecimal aTotalSourceAmount, BigDecimal aTotalTargetAmount) {
		exchangeSymbol = aExchangeSymbol;
		transactionType = aTransactionType;
		totalSourceAmount = aTotalSourceAmount;
		totalTargetAmount = aTotalTargetAmount;
	}
	
	public String getExchangeSymbol() {
		return exchangeSymbol;
	}
	public void setExchangeSymbol(String aExchangeSymbol) {
		exchangeSymbol = aExchangeSymbol;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String aTransactionType) {
		transactionType = aTransactionType;
	}
	public BigDecimal getTotalSourceAmount() {
		return totalSourceAmount;
	}
	public void setTotalSourceAmount(BigDecimal aTotalSourceAmount) {
		totalSourceAmount = aTotalSourceAmount;
	}
	public BigDecimal getTotalTargetAmount() {
		return totalTargetAmount;
	}
	public void setTotalTargetAmount(BigDecimal aTotalTargetAmount) {
		totalTargetAmount = aTotalTargetAmount;
	}
}