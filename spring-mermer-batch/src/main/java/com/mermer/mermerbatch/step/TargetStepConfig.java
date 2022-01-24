
package com.mermer.mermerbatch.step;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.batch.core.ExitStatus;
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
import com.mermer.mermerbatch.core.entity.LawInfo;
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
	
	
	/*
	 * ExecutionContext에 저장할 데이터
	 * 1. law id -> 다음 스텝에 이용할 값
	 * 2. domain List -> 도메인 리스트
	 * 3. item count
	 */
	@StepScope
	@Bean
	public Tasklet targetListTasklet(
			@Value("#{jobParameters['search']}") String search,
			@Value("#{jobParameters['query']}") String query
			) {
		
			return ((contribution, chunkContext) -> {
		
				StepExecution stepExecution = chunkContext.getStepContext().getStepExecution();
				ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
				 

				//데이터가 있으면 다음 스텝을 실행하도록 하고, 데이터가 없으면 종료되도록 한다
				//데이터가 있으면 -> CONTINUABLE
				// 1. domainList
				// 2. lawId
				// 3. itemCount
				
				//데이터가 없을때만 transaction
				List<Integer> lowInfoList = null;
				if(!executionContext.containsKey("lawInfoList")){
					lowInfoList = domainRepository.findByLawName(query);
					executionContext.put("lawInfoList", lowInfoList);
					executionContext.putInt("itemCount", lowInfoList.size());
				}else {
					lowInfoList = (List<Integer>) executionContext.get("lawInfoList");
				}
				
				Integer itemCount = executionContext.getInt("itemCount");
				
				if(itemCount == 0) {
					contribution.setExitStatus(ExitStatus.COMPLETED);
					return RepeatStatus.FINISHED;
				}
				itemCount--;
				
				executionContext.putInt("itemCount", itemCount);
				Integer lawId = lowInfoList.get(itemCount);
				
				executionContext.putInt("lawId", lawId);
				contribution.setExitStatus(new ExitStatus("CONTINUABLE"));
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
			log.info("[targetPrintTasklet[lawId]]::" + lawId);
			return RepeatStatus.FINISHED;
		});
	}
	
}
