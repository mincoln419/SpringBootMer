
package com.mermer.cm.repository.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.mermer.cm.entity.Notice;
import com.mermer.cm.entity.NoticeAbstract;
import com.mermer.cm.entity.QNotice;
import com.mermer.cm.entity.type.UseYn;
import com.mermer.cm.repository.Querydsl4RepositorySupport;
import com.mermer.cm.repository.SearchNoticeRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;

/**
 * @packageName : com.mermer.cm.repository.impl
 * @fileName : SearchNoticeRepositoryImpl.java 
 * @author : Mermer 
 * @date : 2022.01.14 
 * @description : notice repo dsl query 구현 클래스
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.14 Mermer 최초 생성
 */
@Repository
public class SearchNoticeRepositoryImpl extends Querydsl4RepositorySupport implements SearchNoticeRepository {

	public SearchNoticeRepositoryImpl() {
		super(Notice.class);
	}
	
	
	@Override
	public Page<NoticeAbstract> findAllNoContent(Pageable pageable) {
		
		QNotice notice = QNotice.notice;
		
		JPQLQuery<NoticeAbstract> tuple = select(Projections.constructor(NoticeAbstract.class,
																		notice.id,
																		notice.title,
																		notice.readCnt,
																		notice.instDtm,
																		notice.mdfDtm,
																		notice.inster.id)
																		).from(notice);
		BooleanBuilder conditionBuilder = new BooleanBuilder();
		 
		conditionBuilder.and(notice.useYn.eq(UseYn.Y));
		tuple.where(conditionBuilder);
		
		tuple.offset(pageable.getOffset());
		tuple.limit(pageable.getPageSize());
		
		List<NoticeAbstract> result = tuple.fetch();
		//long count = tuple.fetchCount(); -> count 쿼리를 달리 만들 경우에 더 의미가 있음
		//count 최적화를 위함 - 본 쿼리에서 join 이 많을 경우에 성능 개선이 될 수 있음
		//Page<NoticeAbstract> pageList = new PageImpl(result , pageable, count);
		
		return PageableExecutionUtils.getPage(result, pageable, tuple::fetchCount); // count 쿼리가 필요 없는 경우 fetchcount를 호출하지 않음
	}

}
