
package com.mermer.mermerbatch.core.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mermer.mermerbatch.core.entity.PageWork;

/**
 * @packageName : com.mermer.mermerbatch.core.entity.repository
 * @fileName : PageWorkRepository.java 
 * @author : Mermer 
 * @date : 2022.01.17 
 * @description : 다수 페이지 작업을 위한 임시저장 페이지
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.17 Mermer 최초 생성
 */
public interface PageWorkRepository extends JpaRepository<PageWork, Long>{

}
