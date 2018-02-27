package com.mausoft.common.util;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.mausoft.common.model.IModel;

public abstract class SpecificationsBuilder {
	
	@SuppressWarnings("rawtypes")
	public static final <T> Specification equal(T leftValue, T rightValue){
		return (r, q, c) -> c.equal(c.literal(leftValue), rightValue);
	}
	
	public static final <T extends IModel, V> Specification<T> equal(String propertyName, V value, Class<T> clazz){
		return (r, q, c) -> c.equal(_buildPropertyPath(r, propertyName), value);
	}
	
	public static final <T extends IModel> Specification<T> like(String propertyName, String value, Class<T> clazz){
		return (r, q, c) -> c.like(_buildPropertyPath(r, propertyName), value);
	}
	
	private static final <T extends IModel> Path<String> _buildPropertyPath(Root<T> root, String propertyName){
		String[] propertyNameTokens = null;
		Path<String> propertyPath = null;
		
		if (propertyName != null) {
			propertyNameTokens = propertyName.split("\\.");
			
			propertyPath = root.get(propertyNameTokens[0]);
			
			if (propertyNameTokens.length > 1) {
				
				for (int index = 1; index < propertyNameTokens.length; index++) {
					propertyPath = propertyPath.get(propertyNameTokens[index]);
				}
			}
		}
		
		return propertyPath;
	}
}