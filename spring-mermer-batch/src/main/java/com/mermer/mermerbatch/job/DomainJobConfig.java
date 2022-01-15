
package com.mermer.mermerbatch.job;

import java.util.Collections;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import com.mermer.mermerbatch.core.entity.Domain;
import com.mermer.mermerbatch.core.entity.repository.DomainRepository;

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
public class DomainJobConfig {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final DomainRepository domainRepository;
	@Bean("domainJob")
	public Job domainJob(Step readStep) {
		return jobBuilderFactory.get("domainJob")
				.incrementer(new RunIdIncrementer())
				.start(readStep)
				.build();
	}
	
	
	@SuppressWarnings("unchecked")
	@JobScope // Job이 실행되는 동안에만 step의 객체가 살아있도록
	@Bean("readStep")
	public Step readStep(ItemReader domainReader,
					     ItemProcessor<Domain, String> itemProcessor,
					     ItemWriter<String> itemWriter
						) {
		return stepBuilderFactory.get("readStep")
				.<Domain, String>chunk(5)
				.reader(domainReader)
				.processor(itemProcessor)
				.writer(itemWriter)
				.build();
	}
	
	@StepScope
	@Bean
	public RepositoryItemReader<Domain> domainReader(){
		
		return new RepositoryItemReaderBuilder<Domain>()
				.name("domainReader")
				.repository(domainRepository)
				.methodName("findBy")
				.pageSize(5)
				.arguments(List.of())
				.sorts(Collections.singletonMap("id", Sort.Direction.DESC))
				.build();
	}
	
	@StepScope
	@Bean
	public ItemProcessor<Domain, String> domainProcessor(){
		
		return item -> "processed" + item.getContent();
	}
	
	
	@StepScope
	@Bean
	public ItemWriter<String> domainWriter(){
		
		return items -> {
			items.forEach(System.out::println);
			System.out.println("-=== chunk is finished");
			
		};
	}
	
	
	
}
