/**
 * @packageName : com.mermer.mermerbatch.job
 * @fileName : ReadStep.java 
 * @author : Mermer 
 * @date : 2022.01.19 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.19 Mermer 최초 생성
 */
package com.mermer.mermerbatch.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.listener.CompositeItemReadListener;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

import com.mermer.mermerbatch.adaptor.LawDomainAPIResource;
import com.mermer.mermerbatch.core.entity.Domain;
import com.mermer.mermerbatch.core.entity.dto.DomainDto;
import com.mermer.mermerbatch.core.entity.repository.DomainRepository;
import com.mermer.mermerbatch.core.entity.repository.PageWorkRepository;
import com.mermer.mermerbatch.core.entity.type.StepType;
import com.mermer.mermerbatch.service.DomainService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class ReadStepConfig {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private DomainRepository domainRepository;
	
	@Autowired
	private PageWorkRepository pageRepository;
	
	@Autowired
	private LawDomainAPIResource lawDomainAPIResource;
	
	@Autowired
	private DomainService domainService;

	@JobScope // Job이 실행되는 동안에만 step의 객체가 살아있도록
	@Bean("readStep")
	public Step readStep(StaxEventItemReader<DomainDto> domainReader,
					     ItemProcessor<DomainDto, DomainDto> domainDtoProcessor,
					     ItemWriter<DomainDto> domainWriter
						) {
		return stepBuilderFactory.get("readStep")
				.<DomainDto, DomainDto>chunk(10)
				.reader(domainReader)
				.processor(domainDtoProcessor)
				.writer(domainWriter)
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
	@Bean("domainReader")
	public StaxEventItemReader<DomainDto> domainReader(
			@Value("#{jobParameters['search']}") String search,
			@Value("#{jobParameters['query']}") String query,
			Jaxb2Marshaller domainMarshaller
			){
		//if(pageRepository.findBySearchAndQuery(search, query).t)
		Resource resource = lawDomainAPIResource.getResource(search, query, StepType.PAGE);
		
		return new StaxEventItemReaderBuilder<DomainDto>()
				.name("domainReader")
				.resource(resource)
				.addFragmentRootElements("law")
				.unmarshaller(domainMarshaller)//마셜러-> xml 문서 데이터를 객체에 매핑해주는 역할
				.build();
	}
	
	@StepScope
	@Bean("domainProcessor")
	public ItemProcessor<DomainDto, DomainDto> domainDtoProcessor(){
		System.out.println("processor");
		return item -> item;
	}
	
	
	@StepScope
	@Bean
	public Jaxb2Marshaller domainMarshaller() {		
		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		jaxb2Marshaller.setClassesToBeBound(DomainDto.class);
		return jaxb2Marshaller;
	}
	
	
	@StepScope
	@Bean("domainWriter")
	public ItemWriter<DomainDto> domainWriter(){
		
		return items -> {
			items.forEach(item -> {
				domainService.saveDomainInfo(item);
			});
		};
	}
}
