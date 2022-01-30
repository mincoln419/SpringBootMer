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
	private LawInstanceRepository criminalLawRepository;
	
	@BeforeEach
	public void before() {
		queryFactory = new JPAQueryFactory(em);
		
		LawDomain sample = LawDomain.builder()
						   .content("test")
						   .lawDomainCode("11111")
						   .lawDomainName("형법")
						   .build();
		LawDomain domain = domainRepository.save(sample);
		CriminalLaw lawA = CriminalLaw.builder()
						   .articleNum(10)
						   .domain(domain)
						   .purnishment("처벌한다")
						   .build();
		criminalLawRepository.save(lawA);
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
		
		assertThat(findCrime.getPurnishment()).isEqualTo("처벌한다");
		
	}
}
