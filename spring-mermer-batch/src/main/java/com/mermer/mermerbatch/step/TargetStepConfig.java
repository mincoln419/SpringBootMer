
package com.mermer.mermerbatch.step;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mermer.mermerbatch.core.entity.Domain;
import com.mermer.mermerbatch.core.entity.repository.DomainRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @packageName : com.mermer.mermerbatch.step
 * @fileName : TargetStepConfig.java 
 * @author : Mermer 
 * @date : 2022.01.23 
 * @description : 디테일 다 건에 대한 처리를 위한 대상선정 스텝
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.23 Mermer 최초 생성
 */
@RequiredArgsConstructor
@Configuration
@EnableBatchProcessing
@Slf4j
public class TargetStepConfig {

	private final StepBuilderFactory stepBuilderFactory;
	private final DomainRepository domainRepository;
	
	
	@JobScope // Job이 실행되는 동안에만 step의 객체가 살아있도록
	@Bean("targetStep")
	public Step targetStep(Tasklet targetListTasklet) {
		return stepBuilderFactory.get("helloStep")
				.tasklet(targetListTasklet)
				.build();
	}
	
	@StepScope
	@Bean
	public Tasklet targetListTasklet(
			@Value("#{jobParameters['search']}") String search,
			@Value("#{jobParameters['query']}") String query
			) {
		
			return ((constribution, chunkContext) -> {
		
				StepExecution stepExecution = chunkContext.getStepContext().getStepExecution();
				ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
				
				List<Domain> domainQueue = null; 

				
				executionContext.putString("lawId", "test");
				
				return RepeatStatus.FINISHED;
			});
		
	}
	
	@JobScope // Job이 실행되는 동안에만 step의 객체가 살아있도록
	@Bean("targetPringStep")
	public Step targetPringStep(Tasklet targetPrintTasklet) {
		return stepBuilderFactory.get("helloStep")
				.tasklet(targetPrintTasklet)
				.build();
	}
	
	
	@StepScope
	@Bean
	public Tasklet targetPrintTasklet(
				@Value("#{jobExecutionContext['lawId']}") String lawId
			) {
		return ((contribution, chunkContext) ->{
			System.out.println("domainQueue" + lawId);
			return RepeatStatus.FINISHED;
		});
	}
	
}
