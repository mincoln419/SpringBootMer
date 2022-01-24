/**
 * @packageName : com.mermer.mermerbatch.core.entity.repository
 * @fileName : XmlRepository.java 
 * @author : Mermer 
 * @date : 2022.01.24 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.24 Mermer 최초 생성
 */
package com.mermer.mermerbatch.core.entity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mermer.mermerbatch.core.entity.Xmls;


public interface XmlRepository extends JpaRepository<Xmls, Long>{

	/**
	 * @param int1
	 * @return
	 */
	Optional<Xmls> findByLawId(int LawId);

}
