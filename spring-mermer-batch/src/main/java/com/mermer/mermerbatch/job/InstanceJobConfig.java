
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
	public Job domainJob(Step pageStep, Step readStep, Step articleStep) {
		return jobBuilderFactory.get("domainJob")
				.incrementer(new RunIdIncrementer())
				.validator(domainJobParameterValidator())
				.start(articleStep)
				.on("CONTINOUS").to(articleStep).from(articleStep)
				.on("*").end()
				.end()
				.build();
	}
	
	
	private JobParametersValidator domainJobParameterValidator() {
		CompositeJobParametersValidator validator = new CompositeJobParametersValidator();
		validator.setValidators(Arrays.asList(
//				new FilePathParameterValidator(),
				new NumberTypeParameterValidator()

				));
		return validator;
	}
	
}
