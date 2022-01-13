
package com.mermer.cm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mermer.cm.entity.NoticeAbstract;

/**
 * @packageName : com.mermer.cm.repository
 * @fileName : SearchNoticeRepository.java 
 * @author : Mermer 
 * @date : 2022.01.14 
 * @description : dsl 적용을 위한 repo interface
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.14 Mermer 최초 생성
 */
public interface SearchNoticeRepository {
	Page<NoticeAbstract> findAllNoContent(Pageable pageable);
}
