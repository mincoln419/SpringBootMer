
package com.mermer.cm.repository;

import java.util.List;
import java.util.function.Function;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

/**
 * @packageName : com.mermer.cm.repository
 * @fileName : Querydsl4RepositorySupport.java 
 * @author Younghan Kim 
 * @date : 2022.02.02 
 * @description : Querydsl 4.x 버전에 맞춘 Querydsl 지원 라이브러리 *
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.02.02 Mermer 최초 생성 - 김영한 강사 코드 copy
 */
@Repository
public abstract class Querydsl4RepositorySupport {
	@SuppressWarnings("rawtypes")
	private final Class domainClass;
	private Querydsl querydsl;
	private EntityManager entityManager;
	private JPAQueryFactory queryFactory;

	public Querydsl4RepositorySupport(Class<?> domainClass) {
		Assert.notNull(domainClass, "Domain class must not be null!");
		this.domainClass = domainClass;
	}

	
	//상속받은 도메인의 entityManager 와 JPAQueryFactory 세팅
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Autowired
	public void setEntityManager(EntityManager entityManager) {
		Assert.notNull(entityManager, "EntityManager must not be null!");
		
		JpaEntityInformation entityInformation = JpaEntityInformationSupport.getEntityInformation(domainClass,
				entityManager);
		SimpleEntityPathResolver resolver = SimpleEntityPathResolver.INSTANCE;
		EntityPath path = resolver.createPath(entityInformation.getJavaType());
		this.entityManager = entityManager;
		this.querydsl = new Querydsl(entityManager, new PathBuilder<>(path.getType(), path.getMetadata()));
		this.queryFactory = new JPAQueryFactory(entityManager);
	}

	@PostConstruct
	public void validate() {
		Assert.notNull(entityManager, "EntityManager must not be null!");
		Assert.notNull(querydsl, "Querydsl must not be null!");
		Assert.notNull(queryFactory, "QueryFactory must not be null!");
	}

	protected JPAQueryFactory getQueryFactory() {
		return queryFactory;
	}

	protected Querydsl getQuerydsl() {
		return querydsl;
	}

	protected <T> JPAQuery<T> select(Expression<T> expr) {
		return getQueryFactory().select(expr);
	}

	protected <T> JPAQuery<T> selectFrom(EntityPath<T> from) {
		return getQueryFactory().selectFrom(from);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected <T> Page<T> applyPagination(Pageable pageable, Function<JPAQueryFactory, JPAQuery> contentQuery) {
		JPAQuery jpaQuery = contentQuery.apply(getQueryFactory());
		List<T> content = getQuerydsl().applyPagination(pageable, jpaQuery).fetch();
		return PageableExecutionUtils.getPage(content, pageable, jpaQuery::fetchCount);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected <T> Page<T> applyPagination(Pageable pageable, Function<JPAQueryFactory, JPAQuery> contentQuery,
			Function<JPAQueryFactory, JPAQuery> countQuery) {
		JPAQuery jpaContentQuery = contentQuery.apply(getQueryFactory());
		List<T> content = getQuerydsl().applyPagination(pageable, jpaContentQuery).fetch();
		JPAQuery countResult = countQuery.apply(getQueryFactory());
		return PageableExecutionUtils.getPage(content, pageable, countResult::fetchCount);
	}
}
