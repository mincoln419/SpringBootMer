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
import com.mermer.law.entity.LawInstance;
import com.mermer.law.entity.QCriminalLaw;
import com.mermer.law.entity.QLawInstance;
import com.mermer.law.entity.dto.SelectLawDto;
import com.mermer.law.repository.SearchLawInstaceRepository;
import com.querydsl.core.types.Projections;
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
	public List<SelectLawDto> selectCriminalLaws(CriminalLaw input) {
		
		QCriminalLaw criminalLaw = QCriminalLaw.criminalLaw;
		
		JPQLQuery<CriminalLaw> jpqlQuery = from(criminalLaw);
		
		JPQLQuery<SelectLawDto> tuple = jpqlQuery.select(Projections.constructor(SelectLawDto.class,
																		criminalLaw.id,
																		criminalLaw.articleNum,																		
																		criminalLaw.purnishment,
																		criminalLaw.domain					
																		)
												)
												;
		
		
		List<SelectLawDto> list = tuple.fetch();
		
		return list;
	} 
	


	
	
	

}
