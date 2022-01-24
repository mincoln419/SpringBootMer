
package com.mermer.mermerbatch.job;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.batch.runtime.StepExecution;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.CompositeJobParametersValidator;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.jaxb.SpringDataJaxb.PageDto;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.mermer.mermerbatch.adaptor.LawDomainAPIResource;
import com.mermer.mermerbatch.core.entity.Domain;
import com.mermer.mermerbatch.core.entity.dto.DomainDto;
import com.mermer.mermerbatch.core.entity.repository.DomainRepository;
import com.mermer.mermerbatch.core.entity.type.StepType;
import com.mermer.mermerbatch.validator.FilePathParameterValidator;
import com.mermer.mermerbatch.validator.NumberTypeParameterValidator;
import com.mermer.mermerbatch.validator.SearchTypeParameterValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @packageName : com.mermer.mermerbatch.job
 * @fileName : DomainJobConfig.java 
 * @author : Mermer 
 * @date : 2022.01.14 
 * @description : 
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.14 Mermer 최초 생성
 */
@Configuration
@EnableBatchProcessing
@Slf4j
@RequiredArgsConstructor
public class DomainJobConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	


	
	@Bean("domainJob")
	public Job domainJob(
			JobExecutionListener jobExecutionListener,
			Step pageStep,
			Step readStep) {
		return jobBuilderFactory.get("domainJob")
				.incrementer(new RunIdIncrementer())
				.validator(domainJobParameterValidator())
				.listener(jobExecutionListener)
				.start(pageStep)
				.next(readStep)
				//.on("COUNTINUOS").to(readStep).next(readStep)
				//.end()
				.build();
	}
	
	@JobScope
	@Bean
	public JobExecutionListener jobExecutionListener() {
		return new JobExecutionListener() {
			
			@Override
			public void beforeJob(JobExecution jobExecution) {
				log.info("[JobExecutionListenerBeforeJob] jobExcution is " + jobExecution.getStatus());
				
			}
			
			@Override
			public void afterJob(JobExecution jobExecution) {
				if(jobExecution.getStatus() == BatchStatus.FAILED) {
					//TODO 배치가 실패했을 때 특별한 액션을 설정해둘 수 있음
					log.info("[JobExecutionListenerAfterJob] jobExcution is " + jobExecution.getStatus());
				}
			}
		};
	}
	
	
	private JobParametersValidator domainJobParameterValidator() {
		CompositeJobParametersValidator validator = new CompositeJobParametersValidator();
		validator.setValidators(Arrays.asList(
//				new FilePathParameterValidator(),
				new NumberTypeParameterValidator(),
				new SearchTypeParameterValidator()
				));
		return validator;
	}
	

	
	
	
}
