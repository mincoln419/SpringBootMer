/**
 * @packageName : com.mermer.cm.service
 * @fileName : NoticeReplyRepository.java 
 * @author : Mermer 
 * @date : 2021.12.23 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.23 Mermer 최초 생성
 */
package com.mermer.cm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mermer.cm.entity.Reply;

public interface NoticeReplyRepository extends JpaRepository<Reply, Long> {

}
