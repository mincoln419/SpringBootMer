
package com.mermer.mermerbatch.job;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mermer.mermerbatch.BatchTestConfig;
import com.mermer.mermerbatch.adaptor.LawDomainAPIResource;
import com.mermer.mermerbatch.core.entity.PageWork;
import com.mermer.mermerbatch.core.entity.repository.DomainRepository;
import com.mermer.mermerbatch.core.entity.repository.InstanceRepository;
import com.mermer.mermerbatch.core.entity.repository.PageWorkRepository;
import com.mermer.mermerbatch.core.entity.type.StepType;
import com.mermer.mermerbatch.core.entity.type.UseYn;
import com.mermer.mermerbatch.service.DomainService;
import com.mermer.mermerbatch.step.PageStepConfig;
import com.mermer.mermerbatch.step.ReadStepConfig;

/**
 * @packageName : com.mermer.mermerbatch.job
 * @fileName : DomainJobConfigTest.java 
 * @author : Mermer 
 * @date : 2022.01.19 
 * @description : domain ???????????? ???????????? ?????? ????????? 
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.19 Mermer ?????? ??????
 */

@SpringBatchTest
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {
		  DomainJobConfig.class
		, BatchTestConfig.class
		, PageStepConfig.class
		, ReadStepConfig.class
		})
public class DomainJobConfigTest {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@MockBean
	private DomainRepository domainRepository;
	
	@MockBean
	private PageWorkRepository pageWorkRepository;
	
	@MockBean
	private InstanceRepository instanceRepository;
	
	@MockBean
	private LawDomainAPIResource lawDomainAPIResource;
	
	@MockBean
	private DomainService domainService;

	//??????????????? ?????? API??? ???????????? ????????? ?????? ?????? ???????????? ?????? ????????? sample.xml ??? MockMVC??? ?????? ????????? ???????????? ???????????? ????????? ??????
	@Test
	@DisplayName("????????? ????????? ?????? ?????? ???????????? ?????????")
	public void success() throws Exception {
		String search = "1";
		String query = "??????";
		
		//given
		PageWork pageInfo = PageWork.builder()
				.total(10L)
				.useYn(UseYn.Y)
				.page(20L)
				.rows(10L)
				.targetCnt(1L)
				.build();
		when(pageWorkRepository.findBySearchAndQuery(anyString(), anyString())).thenReturn(Optional.of(pageInfo));
		when(lawDomainAPIResource.getResource(anyString(), anyString(), any())).thenReturn(
				new ClassPathResource("sample.xml"));
		
		
		//when
		JobParameters param = new JobParametersBuilder()
							  .addString("search", search)
							  .addString("query",query)
							  .toJobParameters();
		JobExecution execution = jobLauncherTestUtils.launchJob(param);
		
		
		//then
		assertEquals(execution.getExitStatus(), ExitStatus.COMPLETED);
		verify(domainService, times(1)).savePageWork(any(), anyString(), anyString());
		verify(domainService, times(20)).saveDomainInfo(any());
		//assertEquals(domainRepository.count(), 20);
	}
	
	
	@Test
	@DisplayName("??????????????? ?????? ??? ?????? ?????? ?????????")
	public void Error_no_search_arguemnt() throws Exception {
		//when
		JobParameters param = new JobParametersBuilder()
							  .addString("search", null)
							  .toJobParameters();
		try {
			jobLauncherTestUtils.launchJob(param);
		} catch (Exception e) {
			assertEquals(e.getMessage(), "??????????????? ??? ????????????.");
		} 	
	}

	
	
	@Test
	@DisplayName("???????????? null ?????? ??????????????? ???????????? ???????????? ??? ?????????")
	public void Error_no_query_arguemnt() throws Exception {
		//when
		JobParameters param = new JobParametersBuilder()
							  .addString("search", "1")
							  .toJobParameters();
		try {
			jobLauncherTestUtils.launchJob(param);
		} catch (Exception e) {
			assertEquals(e.getMessage(), "?????? ????????? ????????? ??? ????????????");
		} 	
	}
	
	
	@AfterEach
	public void tearDown() {
		domainRepository.deleteAll();
		pageWorkRepository.deleteAll();
	}
}
