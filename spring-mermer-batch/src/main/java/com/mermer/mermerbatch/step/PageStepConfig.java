/**
 * @packageName : com.mermer.mermerbatch.job
 * @fileName : CommonStep.java 
 * @author : Mermer 
 * @date : 2022.01.17 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.17 Mermer 최초 생성
 */
package com.mermer.mermerbatch.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

import com.mermer.mermerbatch.adaptor.LawDomainAPIResource;
import com.mermer.mermerbatch.core.entity.PageWork;
import com.mermer.mermerbatch.core.entity.dto.LawSearchDto;
import com.mermer.mermerbatch.core.entity.repository.PageWorkRepository;
import com.mermer.mermerbatch.core.entity.type.StepType;
import com.mermer.mermerbatch.service.DomainService;


@Component
@Configuration
public class PageStepConfig {
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private LawDomainAPIResource lawDomainAPIResource;
	
	@Autowired
	private DomainService domainService;
	
	@JobScope // Job이 실행되는 동안에만 step의 객체가 살아있도록
	@Bean("pageStep")
	public Step pageStep(StaxEventItemReader<LawSearchDto> pageSerchReader,
					     ItemWriter<LawSearchDto> pageWriter
						) {
		return stepBuilderFactory.get("pageStep")
				.<LawSearchDto, LawSearchDto>chunk(10)
				.reader(pageSerchReader)
				//.processor(itemProcessor)
				.writer(pageWriter)
				.build();
	}
	

	@StepScope
	@Bean("pageSerchReader")
	public StaxEventItemReader<LawSearchDto> pageSerchReader(
			@Value("#{jobParameters['search']}") String search,
			@Value("#{jobParameters['query']}") String query,
			Jaxb2Marshaller domainMarshaller, Unmarshaller pageMarshaller
			){
		
		Resource resource = lawDomainAPIResource.getResource(search, query, StepType.PAGE);
		
		return new StaxEventItemReaderBuilder<LawSearchDto>()
				.name("pageDtoReader")
				.resource(resource)
				.addFragmentRootElements("LawSearch")
				.unmarshaller(pageMarshaller)//마셜러-> xml 문서 데이터를 객체에 매핑해주는 역할
				.build();
	}
	
	
	@StepScope
	@Bean
	public Jaxb2Marshaller pageMarshaller() {		
		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		jaxb2Marshaller.setClassesToBeBound(LawSearchDto.class);
		return jaxb2Marshaller;
	}
	
	
	@StepScope
	@Bean("pageWriter")
	public ItemWriter<LawSearchDto> pageWriter(
			@Value("#{jobParameters['search']}") String search,
			@Value("#{jobParameters['query']}") String query
			){
		
		return items -> {
			items.forEach(item -> {
				System.out.println("item:" + item.toString());
				domainService.savePageWork(item, search, query);

			});
		};
	}
	
}
