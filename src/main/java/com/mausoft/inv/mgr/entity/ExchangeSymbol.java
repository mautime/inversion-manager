package com.mausoft.inv.mgr.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.mausoft.common.entity.DefaultKeyValueEntity;

@Entity
@AttributeOverrides(value= {
		@AttributeOverride(name="name", column=@Column(name="symbolCode", nullable=false)), 
		@AttributeOverride(name="value", column=@Column(name="symbolDescription"))})
public class ExchangeSymbol extends DefaultKeyValueEntity<String> {
	private static final long serialVersionUID = 1094900268769386873L;
	
	public ExchangeSymbol() {}
	
	public ExchangeSymbol(String symbolCode, String symbolDecription, ExchangeSymbolType aSymbolType) {
		setSymbolCode(symbolCode);
		setSymbolDescription(symbolDecription);
		setSymbolType(aSymbolType);
	}
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="symbol_type_id", updatable=false)
	private ExchangeSymbolType symbolType;
	
	public String getSymbolCode() {
		return getName();
	}
	public void setSymbolCode(String aSymbolCode) {
		setName(aSymbolCode);
	}
	public void setSymbolDescription(String symbolDescription) {
		setValue(symbolDescription);
	}
	public String getSymbolDescription() {
		return getValue();
	}
	public ExchangeSymbolType getSymbolType() {
		return symbolType;
	}
	public void setSymbolType(ExchangeSymbolType aSymbolType) {
		symbolType = aSymbolType;
	}
}