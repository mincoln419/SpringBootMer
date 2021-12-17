
package com.mermer.cm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mermer.cm.entity.Notice;

/**
 * @packageName : com.mermer.cm.repository
 * @fileName : NoticeRepository.java 
 * @author : Mermer 
 * @date : 2021.12.17 
 * @description : notice crud를 위한 repository
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.17 Mermer 최초 생성
 */
public interface NoticeRepository extends JpaRepository<Notice, Long>{

	
}
