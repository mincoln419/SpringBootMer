
package com.mermer.law.repository;

import java.util.List;

import com.mermer.law.entity.CriminalLaw;
import com.mermer.law.entity.LawInstance;

/**
 * @packageName : com.mermer.law.repository
 * @fileName : SearchLawInstaceRepository.java 
 * @author : Mermer 
 * @date : 2022.01.14 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.14 Mermer 최초 생성
 */
public interface SearchLawInstaceRepository {

	
	public List<CriminalLaw> ciminalLaws(LawInstance instance);
}
