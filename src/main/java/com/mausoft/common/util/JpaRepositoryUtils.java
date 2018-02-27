package com.mausoft.common.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.mausoft.common.model.IModel;
import com.mausoft.common.model.PaginationSearch;

public abstract class JpaRepositoryUtils {
	
	public static <T extends IModel> Pageable buildPageRequest(PaginationSearch<T> paginationSearch) {
		Sort sort = null;
		PageRequest pageRequest = null;
		
		if (paginationSearch != null) {
			
			if (StringUtils.isNotBlank(paginationSearch.getSort())) {
				sort = new Sort(paginationSearch.getDir() != null && paginationSearch.getDir().equals(PaginationSearch.SortDirection.ASC) ? Direction.ASC : Direction.DESC, paginationSearch.getSort());
			}
			
			pageRequest = new PageRequest(paginationSearch.getOffset() / paginationSearch.getMax(), paginationSearch.getMax(), sort);
		}
		
		return pageRequest;
	}
}