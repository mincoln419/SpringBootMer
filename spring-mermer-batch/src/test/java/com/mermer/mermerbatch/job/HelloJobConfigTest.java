
package com.mermer.mermerbatch.job;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mermer.mermerbatch.BatchTestConfig;
import com.mermer.mermerbatch.adaptor.LawDomainAPIResource;

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
@ContextConfiguration(classes = {HelloJobConfig.class, BatchTestConfig.class})
public class HelloJobConfigTest {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;
	
	
	@Test
	@DisplayName("도메인 배치 정상수행 테스트")
	public void success() throws Exception {
		//when
		JobExecution execution = jobLauncherTestUtils.launchJob();
		
		//then
		assertEquals(execution.getExitStatus(), ExitStatus.COMPLETED);
	}
	
	
}
