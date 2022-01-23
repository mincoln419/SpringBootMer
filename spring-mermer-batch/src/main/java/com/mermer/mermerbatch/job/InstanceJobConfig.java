
package com.mermer.mermerbatch.job;

import java.util.Arrays;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.CompositeJobParametersValidator;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.mermer.mermerbatch.validator.NumberTypeParameterValidator;

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
			  Step articleStep, 
			  Step instanceUnWrapStep, 
			  Step targetStep,
			  Step targetPringStep
			) {
		return jobBuilderFactory.get("instanceJob")
				.incrementer(new RunIdIncrementer())
				.start(targetStep) // 상세 데이터를 뽑을 법률 목록 조회 -> context에 세팅
				.next(targetPringStep)
				//.next(instanceWrapperStep) //상세 데이터 메타데이터 처리
				//.next(articleStep) // 상세 데이터 각 조문을 각각 DB에 저장
				.build();
	}
		
}
