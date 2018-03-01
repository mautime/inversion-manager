package com.mausoft.inv.mgr.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.mausoft.common.entity.AbstractDefaultEntity;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class ExchangeTransaction extends AbstractDefaultEntity {
	private static final long serialVersionUID = 8488493767389467642L;
	
	public enum TransactionType {
		SELL, BUY
	}
	
	@Column(nullable=false)
	private BigDecimal sourceAmount;
	@Column(nullable=false)
	private BigDecimal targetAmount;
	@Column(nullable=false)
	private BigDecimal transactionFee;
	@Column(nullable=false)
	private BigDecimal exchangeRate;
	private String symbol;
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;
	private Date transactionDate;
	@CreatedBy
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="created_by", columnDefinition="VARCHAR")
	private User createdBy;
	@CreatedDate
	@Column(nullable=false, updatable=false)
	private Date createdDate;
	@LastModifiedBy
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="last_updated_by")
	private User lastUpdatedBy;
	@LastModifiedDate
	@Column(nullable=false, updatable=false)
	private Date lastUpdatedDate;
	
	public BigDecimal getSourceAmount() {
		return sourceAmount;
	}
	public void setSourceAmount(BigDecimal aSourceAmount) {
		sourceAmount = aSourceAmount;
	}
	public BigDecimal getTargetAmount() {
		return targetAmount;
	}
	public void setTargetAmount(BigDecimal aTargetAmount) {
		targetAmount = aTargetAmount;
	}
	public BigDecimal getTransactionFee() {
		return transactionFee;
	}
	public void setTransactionFee(BigDecimal aTransactionFee) {
		transactionFee = aTransactionFee;
	}
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(BigDecimal aExchangeRate) {
		exchangeRate = aExchangeRate;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String aSymbol) {
		symbol = aSymbol;
	}
	public TransactionType getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(TransactionType aTransactionType) {
		transactionType = aTransactionType;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date aTransactionDate) {
		transactionDate = aTransactionDate;
	}
	public User getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(User aCreatedBy) {
		createdBy = aCreatedBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date aCreatedDate) {
		createdDate = aCreatedDate;
	}
	public User getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(User aLastUpdatedBy) {
		lastUpdatedBy = aLastUpdatedBy;
	}
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Date aLastUpdatedDate) {
		lastUpdatedDate = aLastUpdatedDate;
	}
}