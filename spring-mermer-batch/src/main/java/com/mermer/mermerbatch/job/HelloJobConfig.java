
package com.mermer.mermerbatch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

/**
 * @packageName : com.mermer.mermerbatch.job
 * @fileName : HelloJob.java 
 * @author : Mermer 
 * @date : 2022.01.14 
 * @description : 
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.14 Mermer 최초 생성
 */
@Configuration
@RequiredArgsConstructor
@EnableBatchProcessing
public class HelloJobConfig {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	
	@Bean("helloJob")
	public Job helloJob(Step helloStep) {
		return jobBuilderFactory.get("helloJob")
				.incrementer(new RunIdIncrementer())
				.start(helloStep)
				.build();
	}
	
	@JobScope // Job이 실행되는 동안에만 step의 객체가 살아있도록
	@Bean("helloStep")
	public Step helloStep(Tasklet tasklet) {
		return stepBuilderFactory.get("helloStep")
				.tasklet(tasklet)
				.build();
	}
	
	@StepScope
	@Bean
	public Tasklet tasklet() {
			return ((constribution, chunkContext) -> {
				System.out.println("Hello Spring Batch");
				return RepeatStatus.FINISHED;
			});
		
	}
	
	
}
