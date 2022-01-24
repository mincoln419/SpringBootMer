
package com.mermer.mermerbatch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @packageName : com.mermer.mermerbatch.job
 * @fileName : InstanceJobConfig.java 
 * @author : Mermer 
 * @date : 2022.01.19 
 * @description : 상세법률 데이터 수신 JOB
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.19 Mermer 최초 생성
 */
@Component
@EnableBatchProcessing
public class InstanceJobConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Bean("instanceJob")
	public Job domainJob( 
			  Step instanceStep, 
			  Step targetStep,
			  Step targetPringStep
			) {
		return jobBuilderFactory.get("instanceJob")
				.incrementer(new RunIdIncrementer())
				.start(targetStep) // 상세 데이터를 뽑을 법률 목록 조회 -> context에 세팅
				.on("CONTINUABLE").to(instanceStep).next(targetStep)
				.from(targetStep)
				.on("*").end()
				.end()
				//.next(instanceWrapperStep) //상세 데이터 메타데이터 처리
				//.next(articleStep) // 상세 데이터 각 조문을 각각 DB에 저장
				.build();
	}
		
}
