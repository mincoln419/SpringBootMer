
package com.mermer.cm.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.mermer.cm.entity.Notice;
import com.mermer.cm.entity.NoticeAbstract;

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
public interface NoticeRepository extends JpaRepository<Notice, Long> , SearchNoticeRepository{

	
	/**
	 * @method findAllNoContent
	 * @param pageable
	 * @return
	 * Page<Notice>
	 * @description 공지사항 목록조회시 content 제외한 항목만 조회
	 */
	
	
	Optional<Notice> findById(Long noticeId);
}
