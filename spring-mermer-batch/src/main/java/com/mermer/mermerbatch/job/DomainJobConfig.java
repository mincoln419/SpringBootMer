
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
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.mermer.mermerbatch.adaptor.LawDomainAPIResource;
import com.mermer.mermerbatch.core.entity.Domain;
import com.mermer.mermerbatch.core.entity.DomainDto;
import com.mermer.mermerbatch.core.entity.repository.DomainRepository;
import com.mermer.mermerbatch.validator.FilePathParameterValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class DomainJobConfig {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final DomainRepository domainRepository;
	private final LawDomainAPIResource lawDomainAPIResource;
	
	@Value("${external.law-domain-api.path}")
	private String path;
	
	@Bean("domainJob")
	public Job domainJob(Step readStep) {
		log.debug("path>.." + path);
		return jobBuilderFactory.get("domainJob")
				.incrementer(new RunIdIncrementer())
				.validator(new FilePathParameterValidator())
				.start(readStep)
				.build();
	}
	
	

	@JobScope // Job이 실행되는 동안에만 step의 객체가 살아있도록
	@Bean("readStep")
	public Step readStep(StaxEventItemReader<DomainDto> domainReader,
					     //ItemProcessor<DomainDto, String> itemProcessor,
					     ItemWriter<DomainDto> itemWriter
						) {
		return stepBuilderFactory.get("readStep")
				.<DomainDto, DomainDto>chunk(10)
				.reader(domainReader)
				//.processor(itemProcessor)
				.writer(itemWriter)
				.build();
	}
	

	//sample 파일로 테스트
//	@StepScope
//	@Bean
//	public StaxEventItemReader<DomainDto> domainReader(
//			@Value("#{jobParameters['filePath']}") String filePath,
//			Jaxb2Marshaller domainMarshaller
//			){
//		return new StaxEventItemReaderBuilder<DomainDto>()
//				.name("domainDtoReader")
//				.resource(new ClassPathResource(filePath))
//				.addFragmentRootElements("law")
//				.unmarshaller(domainMarshaller)//마셜러-> xml 문서 데이터를 객체에 매핑해주는 역할
//				.build();
//	}

	@StepScope
	@Bean
	public StaxEventItemReader<DomainDto> domainReader(
			Jaxb2Marshaller domainMarshaller
			){
		log.debug("read-start");
		Resource resource = lawDomainAPIResource.getResource("1", "민법");
		
		log.debug("resource::::::" + resource.toString());
		return new StaxEventItemReaderBuilder<DomainDto>()
				.name("domainDtoReader")
				.resource(resource)
				.addFragmentRootElements("law")
				.unmarshaller(domainMarshaller)//마셜러-> xml 문서 데이터를 객체에 매핑해주는 역할
				.build();
	}
	
	
	@StepScope
	@Bean
	public Jaxb2Marshaller domainMarshaller() {		
		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		jaxb2Marshaller.setClassesToBeBound(DomainDto.class);
		return jaxb2Marshaller;
	}
	
	
	@StepScope
	@Bean
	public ItemWriter<DomainDto> domainWriter(){
		
		return items -> {
			items.forEach(System.out::println);
			System.out.println("-=== chunk is finished");
			
		};
	}
	
	
	
}
