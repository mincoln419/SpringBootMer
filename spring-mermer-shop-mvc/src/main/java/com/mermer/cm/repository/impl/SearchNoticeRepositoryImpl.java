
package com.mermer.cm.repository.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.mermer.cm.entity.Notice;
import com.mermer.cm.entity.NoticeAbstract;
import com.mermer.cm.entity.QNotice;
import com.mermer.cm.entity.type.UseYn;
import com.mermer.cm.repository.SearchNoticeRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.PathBuilder;
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
public class SearchNoticeRepositoryImpl extends QuerydslRepositorySupport implements SearchNoticeRepository {

	public SearchNoticeRepositoryImpl() {
		super(Notice.class);
	}
	
	
	@Override
	public Page<NoticeAbstract> findAllNoContent(Pageable pageable) {
		
		QNotice notice = QNotice.notice;
				
		JPQLQuery<Notice> jpqlQuery = from(notice);
		
		JPQLQuery<NoticeAbstract> tuple = jpqlQuery.select(Projections.constructor(NoticeAbstract.class,
																		notice.id,
																		notice.title,
																		notice.readCnt,
																		notice.instDtm,
																		notice.mdfDtm,
																		notice.inster.id)
																		);
		BooleanBuilder conditionBuilder = new BooleanBuilder();
		 
		conditionBuilder.and(notice.useYn.eq(UseYn.Y));
		tuple.where(conditionBuilder);
		
		tuple.offset(pageable.getOffset());
		tuple.limit(pageable.getPageSize());
		
		List<NoticeAbstract> result = tuple.fetch();
		long count = tuple.fetchCount();
		
		Page<NoticeAbstract> pageList = new PageImpl(result , pageable, count);
		
		return pageList;
	}

}
