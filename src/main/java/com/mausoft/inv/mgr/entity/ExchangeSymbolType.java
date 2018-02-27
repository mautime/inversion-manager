package com.mausoft.inv.mgr.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;

import com.mausoft.common.entity.DefaultKeyValueEntity;

@Entity
@AttributeOverrides(value= {
		@AttributeOverride(name="name", column=@Column(name="typeCode", nullable=false)), 
		@AttributeOverride(name="value", column=@Column(name="typeDescription"))})
public class ExchangeSymbolType extends DefaultKeyValueEntity<String> {
	private static final long serialVersionUID = 5491952925350618440L;

	public ExchangeSymbolType() {}
	
	public ExchangeSymbolType(String typeCode, String typeDescription) {
		super(typeCode, typeDescription);
	}
	
	public void setTypeCode(String typeCode) {
		setName(typeCode);
	}
	public String getTypeCode() {
		return getName();
	}
	public void setTypeDescription(String typeDescription) {
		setValue(typeDescription);
	}
	public String getTypeDescription() {
		return getValue();
	}
}