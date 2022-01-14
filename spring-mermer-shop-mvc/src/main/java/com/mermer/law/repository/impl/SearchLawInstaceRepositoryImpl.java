/**
 * @packageName : com.mermer.law.repository.impl
 * @fileName : SearchLawInstaceRepositoryImpl.java 
 * @author : Mermer 
 * @date : 2022.01.14 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.14 Mermer 최초 생성
 */
package com.mermer.law.repository.impl;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.mermer.law.entity.LawInstance;
import com.mermer.law.repository.SearchLawInstaceRepository;

@Repository
public class SearchLawInstaceRepositoryImpl extends QuerydslRepositorySupport implements SearchLawInstaceRepository{

	/**
	 * @param domainClass
	 */
	public SearchLawInstaceRepositoryImpl(Class<LawInstance> domainClass) {
		super(domainClass);
	}

	@Override
	public List<LawInstance> lawInstances(LawInstance instance) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
