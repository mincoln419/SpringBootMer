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

import com.mermer.law.entity.CriminalLaw;
import com.mermer.law.entity.LawInstance;
import com.mermer.law.entity.QCriminalLaw;
import com.mermer.law.entity.QLawInstance;
import com.mermer.law.repository.SearchLawInstaceRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;

@Repository
public class SearchLawInstaceRepositoryImpl extends QuerydslRepositorySupport implements SearchLawInstaceRepository{

	/**
	 * @param domainClass
	 */
	public SearchLawInstaceRepositoryImpl() {
		super(CriminalLaw.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<CriminalLaw> ciminalLaws(LawInstance instance) {
		
		QCriminalLaw criminalLaw = QCriminalLaw.criminalLaw;
		
		JPQLQuery<CriminalLaw> jpqlQuery = from(criminalLaw);
		
		JPQLQuery<CriminalLaw> tuple = jpqlQuery.select(Projections.constructor(CriminalLaw.class,
																		criminalLaw.id,
																		criminalLaw.articleNum,																		
																		criminalLaw.structure,
																		criminalLaw.purnishment,
																		criminalLaw.domain.id)					
																		);
		
		
		List<CriminalLaw> list = tuple.fetch();
		
		return list;
	}
	


	
	
	

}
