
package com.mermer.law.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mermer.law.entity.LawInstance;

/**
 * @packageName : com.mermer.law.repository
 * @fileName : LawInstanceRepository.java 
 * @author : Mermer 
 * @date : 2022.01.14 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.14 Mermer 최초 생성
 */
public interface LawInstanceRepository extends JpaRepository<LawInstance, Long>{

}
