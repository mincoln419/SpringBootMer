
package com.mermer.mermerbatch.job;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.batch.runtime.BatchStatus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mermer.mermerbatch.BatchTestConfig;
import com.mermer.mermerbatch.adaptor.LawDomainAPIResource;
import com.mermer.mermerbatch.core.entity.repository.DomainRepository;
import com.mermer.mermerbatch.core.entity.repository.InstanceRepository;
import com.mermer.mermerbatch.core.entity.repository.PageWorkRepository;

/**
 * @packageName : com.mermer.mermerbatch.job
 * @fileName : DomainJobConfigTest.java 
 * @author : Mermer 
 * @date : 2022.01.19 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.19 Mermer 최초 생성
 */

@SpringBatchTest
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {DomainJobConfig.class
		, BatchTestConfig.class
		, LawDomainAPIResource.class
		, PageWorkRepository.class
		, DomainRepository.class
		, CommonStep.class
		, InstanceRepository.class})
public class DomainJobConfigTest {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	private DomainRepository domainRepository;
	

	
	@Value("${external.law-domain-api.path}") String path;
	
	@Test
	@DisplayName("도메인 배치 정상수행 테스트")
	public void success() throws Exception {
		
		//when-search=1 -query=형법
		JobParameters param = new JobParametersBuilder()
							  .addString("search", "1")
							  .addString("query", "형법")
							  .toJobParameters();
		JobExecution execution = jobLauncherTestUtils.launchStep("pageStep",param);
		//then
		assertEquals(execution.getExitStatus(), ExitStatus.COMPLETED);
		assertEquals(domainRepository.count(), 0);
	}
	
	
}
