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

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mermer.cm.entity.NoticeList;
import com.mermer.cm.entity.Reply;

public interface NoticeReplyRepository extends JpaRepository<Reply, Long> {

	/**
	 * @method findAllReplyByNoticeId
	 * @param pageable
	 * @param noticeId
	 * @return
	 * Page<Reply>
	 * @description 
	 */
	@Query(nativeQuery = true
		, value = "select *"
		          + "from reply r "
		         + "where r.use_Yn = 'Y'"
		         + " and r.notice_id = ?1", 
		  countQuery = "select * from reply where use_yn = 'Y' and notice_id = ?1"
		  )
	Page<Reply> findAllReplyByNoticeId(Long noticeId, Pageable pageable);

	/**
	 * @method getByIdAndNotice
	 * @param noticeId
	 * @param replyId
	 * @return
	 * Reply
	 * @description 
	 */
	@Query(nativeQuery = true
			, value = "select *"
			          + "from reply r "
			          + "where r.use_Yn = 'Y'"
			          + " and r.notice_id = ?1"
			          + " and r.id = ?2"
			          , 
			  countQuery = "select * from reply where use_yn = 'Y' and notice_id = ?1 and id = ?2"
			  )
	Optional<Reply> getByIdAndNotice(Long noticeId, Long replyId);

}
