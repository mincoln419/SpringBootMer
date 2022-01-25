
package com.mermer.mermerbatch.core.entity.repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mermer.mermerbatch.core.entity.Domain;
import com.mermer.mermerbatch.core.entity.LawInfo;

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


	@Query(nativeQuery = true, value = "select law_Id from tb_lw_domain "
									 + "where use_yn = 'Y' "
									 + "and finished = 'N' "
									 + "and law_name like '%' || ?1  || '%' " )
	List<Integer> findByLawName(String query);

	/**
	 * @param query
	 * @return 
	 */
	@Query(nativeQuery = true, value = "select * from tb_lw_domain "
			 + "where use_yn = 'Y' "
			 + "and finished = 'N' "
			 + "and law_name = ?1" )
	List<LawInfo> findByLawContent(String query);
	
	
	
}
