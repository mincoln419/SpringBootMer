
package com.mermer.mermerbatch.core.entity.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mermer.mermerbatch.core.entity.Domain;

/**
 * @packageName : com.mermer.mermerbatch.core.entity.repository
 * @fileName : DomainRepository.java 
 * @author : Mermer 
 * @date : 2022.01.14 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.14 Mermer 최초 생성
 */
public interface DomainRepository extends JpaRepository<Domain, Long>{
	
	
	Page<Domain> findBy(Pageable pageable);

	/**
	 * @param lawId
	 * @return
	 */
	Optional<Domain> findByLawId(Integer lawId);
}
