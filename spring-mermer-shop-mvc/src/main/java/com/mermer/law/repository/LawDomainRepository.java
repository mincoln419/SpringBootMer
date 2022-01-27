
package com.mermer.law.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mermer.law.entity.LawDomain;

/**
 * @packageName : com.mermer.law.repository
 * @fileName : LawDomainRepository.java 
 * @author : Mermer 
 * @date : 2022.01.27 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.27 Mermer 최초 생성
 */
public interface LawDomainRepository extends JpaRepository<LawDomain, Long>{

}
