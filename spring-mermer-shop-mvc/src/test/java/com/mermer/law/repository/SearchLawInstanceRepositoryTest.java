/**
 * @packageName : com.mermer.law.repository
 * @fileName : SearchLawInstanceRepositoryTest.java 
 * @author : Mermer 
 * @date : 2022.01.27 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.27 Mermer 최초 생성
 */
package com.mermer.law.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mermer.law.entity.CriminalLaw;
import com.mermer.law.entity.LawDomain;
import com.mermer.law.entity.QCriminalLaw;
import com.mermer.law.entity.QLawDomain;
import com.mermer.law.entity.embeded.Structure;
import com.mermer.law.entity.type.LawMethod;
import com.mermer.law.entity.type.LawResult;
import com.mermer.law.entity.type.LawSubject;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

@SpringBootTest
@Transactional
public class SearchLawInstanceRepositoryTest {

	@Autowired
	private EntityManager em;
	
	private JPAQueryFactory queryFactory;
	
	@Autowired
	private LawDomainRepository domainRepository;
	
	@Autowired
	private LawInstanceRepository lawInstanceRepository;
	
	private LawDomain domain;
	
	
	@BeforeEach
	public void before() {
		lawInstanceRepository.deleteAll();
		domainRepository.deleteAll();
		queryFactory = new JPAQueryFactory(em);
		LawDomain sample = LawDomain.builder()
				   .content("test")
				   .lawDomainCode("11111")
				   .lawDomainName("형법")
				   .build();
		domain = domainRepository.save(sample);

		generateLawInstance(10);

	}
	
	
	/**
	 * @method generateLawInstance
	 * @param i
	 * void
	 * @description 
	 */
	private void generateLawInstance(int i) {
		
		String[] arr = {"처벌", "면제", "보호관찰", "치료감호"};
		
		int randInt = ((int)Math.random()) % 4;
		
		CriminalLaw lawA = CriminalLaw.builder()
				   .articleNum(i)
				   .domain(domain)
				   .purnishment(arr[randInt])
				   .structure(Structure.builder()
							.method(LawMethod.DANGER)
							.subject(LawSubject.MAN)
							.object(LawSubject.MAN)
							.result(LawResult.DEAD)
							.build())
				   .build();
		lawInstanceRepository.save(lawA);
	}


	@Test
	@DisplayName("도메인 생성 확인 테스트")
	public void domain_create_success() {
		domainRepository.findAll();
		
		assertThat(domainRepository.count() > 0);
		assertThat(domainRepository.findAll().get(0).getContent()).isEqualTo("test");
	}
	
	
	@Test
	@DisplayName("query DSL 처리 확인")
	public void queryDsl_success() {
		
		QLawDomain domain = new QLawDomain("domain");
		
		LawDomain findDomain = queryFactory
				.select(domain)
				.from(domain)
				.where(domain.lawDomainCode.eq("11111"))
				.fetchOne();
		
		assertThat(findDomain.getContent()).isEqualTo("test");
		
	}
	
	
	@Test
	@DisplayName("query DSL 처리 확인 - 기본 queryDsl 기본 생성자")
	public void queryDsl_contruct_success() {
		
		QLawDomain domain = QLawDomain.lawDomain;
		
		LawDomain findDomain = queryFactory
				.select(domain)
				.from(domain)
				.where(domain.lawDomainCode.eq("11111"))
				.fetchOne();
		
		assertThat(findDomain.getContent()).isEqualTo("test");
		
	}
	
	
	
	@Test
	@DisplayName("query DSL 처리 확인 - law detail")
	public void queryDsl_detail_success() {
		
		QCriminalLaw crime = QCriminalLaw.criminalLaw;
		
		CriminalLaw findCrime = queryFactory
				.select(crime)
				.from(crime)
				.where(crime.articleNum.eq(10))
				.fetchOne();
		
		assertThat(findCrime.getStructure().getSubject()).isEqualTo(LawSubject.MAN);
		
	}
	
	@Test
	@DisplayName("repo query 테스트")
	public void selectCriminalLaws_success() {

		CriminalLaw input = CriminalLaw
				.builder()
				.articleNum(10)
				.build();
		
		List<CriminalLaw> list = lawInstanceRepository.selectCriminalLaws(input);
		
		assertThat(list.get(0).getStructure().getSubject()).isEqualTo(LawSubject.MAN);
	}
	
	@Test
	@DisplayName("집합함수 테스트")
	public void groupFunction_success() {
		
		for(int i = 11 ; i < 21; i++) {
			generateLawInstance(i);
		}
		
		QCriminalLaw crime = QCriminalLaw.criminalLaw;
		
		List<Tuple> result = queryFactory
				.select(crime.count(), crime.domain.lawDomainName)
				.from(crime)
				.orderBy(crime.domain.lawDomainName.asc())
				.groupBy(crime.domain.lawDomainName)
				.fetch();
		Tuple tuple = result.get(0);
		assertThat(tuple.get(crime.count())).isEqualTo(11);
	}
	
	
	@Test
	@DisplayName("case 문 테스트")
	public void case_test_success() {
		
		for(int i = 11 ; i < 21; i++) {
			generateLawInstance(i);
		}
		
		QCriminalLaw crime = QCriminalLaw.criminalLaw;
		
		List<String> result = queryFactory
				.select(new CaseBuilder()
						.when(crime.structure.method.eq(LawMethod.DANGER)).then("위험")
						.when(crime.structure.method.eq(LawMethod.MULTIPLE)).then("공동")
						.otherwise("기타").as("방법")
						)
				.from(crime)
				.orderBy(crime.domain.lawDomainName.asc())
				.fetch();
		
		String tuple = result.get(0);
		assertThat(tuple).isEqualTo("위험");
	}

	@Test
	@DisplayName("프로젝션 사용 - tuple")
	public void projection_tuple_success() {
		for(int i = 11 ; i < 21; i++) {
			generateLawInstance(i);
		}
		
		QCriminalLaw crime = QCriminalLaw.criminalLaw;
		
		List<Tuple> result = queryFactory
				.select(crime.purnishment,
						new CaseBuilder()
						.when(crime.structure.method.eq(LawMethod.DANGER)).then("위험")
						.when(crime.structure.method.eq(LawMethod.MULTIPLE)).then("공동")
						.otherwise("기타").as("방법")
						)
				.from(crime)
				.orderBy(crime.domain.lawDomainName.asc())
				.fetch();
		
		Tuple tuple = result.get(0);
		assertThat(tuple.get(crime.purnishment)).isEqualTo("처벌");
		assertThat(tuple.get(1, String.class)).isEqualTo("위험");
	}
	
	
	@Test
	@DisplayName("boolean Builder 다이나믹 쿼리")
	public void booleanBuilder_success() {
		
		for(int i = 11 ; i < 21; i++) {
			generateLawInstance(i);
		}
		
		QCriminalLaw crime = QCriminalLaw.criminalLaw;
		
		List<Tuple> result = queryFactory
				.select(crime.purnishment,
						new CaseBuilder()
						.when(crime.structure.method.eq(LawMethod.DANGER)).then("위험")
						.when(crime.structure.method.eq(LawMethod.MULTIPLE)).then("공동")
						.otherwise("기타").as("방법")
						)
				.from(crime)
				.orderBy(crime.domain.lawDomainName.asc())
				.fetch();
		
		BooleanBuilder conditions = new BooleanBuilder();
		
		
		Tuple tuple = result.get(0);
		assertThat(tuple.get(crime.purnishment)).isEqualTo("처벌");
		assertThat(tuple.get(1, String.class)).isEqualTo("위험");
		
	}
	
	@Test
	@DisplayName("whereParam 다이나믹 쿼리")
	public void whereParam_success() {
		for(int i = 11 ; i < 21; i++) {
			generateLawInstance(i);
		}
		
		QCriminalLaw crime = QCriminalLaw.criminalLaw;
		
		Integer queryArticleNum = 15;
		String purnishment = "처벌";
		
		List<Tuple> result = queryFactory
				.select(crime.purnishment,
						new CaseBuilder()
						.when(crime.structure.method.eq(LawMethod.DANGER)).then("위험")
						.when(crime.structure.method.eq(LawMethod.MULTIPLE)).then("공동")
						.otherwise("기타").as("방법")
						)
				.from(crime)
				.where(articleNumEq(crime, queryArticleNum), purnishmentEq(crime, purnishment))
				.orderBy(crime.domain.lawDomainName.asc())
				.fetch();
		
		assertThat(result.size()).isGreaterThan(0);
	}


	/**
	 * @param crime 
	 * @method purnishmentEq
	 * @param purnishment
	 * @return
	 * Predicate
	 * @description 
	 */
	private Predicate purnishmentEq(QCriminalLaw crime, String purnishment) {
		
		if(!purnishment.isEmpty())return crime.purnishment.eq(purnishment);
		else return null;
	}


	/**
	 * @param crime 
	 * @method articleNumEq
	 * @param queryArticleNum
	 * @return
	 * Predicate
	 * @description 
	 */
	private Predicate articleNumEq(QCriminalLaw crime, Integer queryArticleNum) {
		
		if(queryArticleNum != null)return crime.articleNum.eq(queryArticleNum);
		else return null;
	}
}
