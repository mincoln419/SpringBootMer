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

import com.mermer.cm.entity.type.UseYn;
import com.mermer.law.entity.CriminalLaw;
import com.mermer.law.entity.QCriminalLaw;
import com.mermer.law.repository.SearchLawInstaceRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;

@Repository
public class SearchLawInstaceRepositoryImpl extends QuerydslRepositorySupport implements SearchLawInstaceRepository{

	/**
	 * @param CriminalLaw
	 */
	public SearchLawInstaceRepositoryImpl() {
		super(CriminalLaw.class);
	}

	@Override
	public List<CriminalLaw> selectCriminalLaws(CriminalLaw input) {
		
		QCriminalLaw criminalLaw = QCriminalLaw.criminalLaw;
		
		JPQLQuery<CriminalLaw> jpqlQuery = from(criminalLaw);
		
		JPQLQuery<CriminalLaw> tuple = jpqlQuery.select(criminalLaw)
												;
		BooleanBuilder conditionBuilder = new BooleanBuilder();
		 
		conditionBuilder.and(criminalLaw.useYn.eq(UseYn.Y));
		conditionBuilder.and(criminalLaw.articleNum.eq(input.getArticleNum()));
		tuple.where(conditionBuilder);
		
		List<CriminalLaw> list = tuple.fetch();
		
		return list;
	} 
	


	
	
	

}
