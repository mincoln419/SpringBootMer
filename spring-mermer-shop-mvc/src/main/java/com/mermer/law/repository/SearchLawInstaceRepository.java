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
package com.mermer.law.repository;

import java.util.List;

import com.google.common.base.Optional;
import com.mermer.law.entity.LawInstance;

import lombok.AllArgsConstructor;


public interface SearchLawInstaceRepository {

	
	public List<LawInstance> lawInstances(LawInstance instance);
}
