
package com.mermer.mermerbatch.job;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mermer.mermerbatch.BatchTestConfig;
import com.mermer.mermerbatch.step.ArticleStepConfig;
import com.mermer.mermerbatch.step.PageStepConfig;
import com.mermer.mermerbatch.step.ReadStepConfig;

/**
 * @packageName : com.mermer.mermerbatch.job
 * @fileName : InstanceJobConfigTest.java 
 * @author : Mermer 
 * @date : 2022.01.21 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.21 Mermer 최초 생성
 */
@SpringBatchTest
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {
		  InstanceJobConfig.class
		, BatchTestConfig.class
		, ArticleStepConfig.class
		})
public class InstanceJobConfigTest {

	
	@Test
	@DisplayName("법률 상세 내용 - 조문 정상 수신 테스트")
	public void success() {
		
		
		
	}
}
