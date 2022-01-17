
package com.mermer.mermerbatch.job;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

import com.mermer.mermerbatch.adaptor.LawDomainAPIResource;
import com.mermer.mermerbatch.adaptor.LawInstanceAPIResource;
import com.mermer.mermerbatch.core.entity.Instance;
import com.mermer.mermerbatch.core.entity.PageWork;
import com.mermer.mermerbatch.core.entity.dto.DomainDto;
import com.mermer.mermerbatch.core.entity.dto.InstanceDto;
import com.mermer.mermerbatch.core.entity.dto.LawSearchDto;
import com.mermer.mermerbatch.core.entity.dto.SubArticleDto;
import com.mermer.mermerbatch.core.entity.embeded.HoArticle;
import com.mermer.mermerbatch.core.entity.repository.PageWorkRepository;
import com.mermer.mermerbatch.core.entity.type.StepType;

import lombok.RequiredArgsConstructor;

/**
 * @packageName : com.mermer.mermerbatch.job
 * @fileName : ArticleStep.java 
 * @author : Mermer 
 * @date : 2022.01.17 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.17 Mermer 최초 생성
 */
@RequiredArgsConstructor
@Component
public class ArticleStepConfig {

	private final StepBuilderFactory stepBuilderFactory;
	private final LawInstanceAPIResource lawInstanceAPIResource;
	
	@JobScope // Job이 실행되는 동안에만 step의 객체가 살아있도록
	@Bean("articleStep")
	public Step articleStep(StaxEventItemReader<InstanceDto> articleReader,
					     ItemWriter<InstanceDto> articleWriter
						) {
		return stepBuilderFactory.get("pageStep")
				.<InstanceDto, InstanceDto>chunk(10)
				.reader(articleReader)
				.writer(articleWriter)
				.build();
	}
	
	

	@StepScope
	@Bean
	public StaxEventItemReader<InstanceDto> articleReader(
			@Value("#{jobParameters['search']}") String search,
			@Value("#{jobParameters['query']}") String query,
			Unmarshaller articleMarshaller
			){
		
		Resource resource = lawInstanceAPIResource.getResource(search, query);
		
		return new StaxEventItemReaderBuilder<InstanceDto>()
				.name("instanceDtoReader")
				.resource(resource)
				.addFragmentRootElements("조문단위")
				.unmarshaller(articleMarshaller)//마셜러-> xml 문서 데이터를 객체에 매핑해주는 역할
				.build();
	}
	
	
	@StepScope
	@Bean
	public Jaxb2Marshaller articleMarshaller() {		
		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		jaxb2Marshaller.setClassesToBeBound(InstanceDto.class, SubArticleDto.class, HoArticle.class);
		return jaxb2Marshaller;
	}
	
	
	@StepScope
	@Bean
	public ItemWriter<InstanceDto> articleWriter(
			@Value("#{jobParameters['search']}") String search,
			@Value("#{jobParameters['query']}") String query
			){
		
		return items -> {
			items.forEach(item -> {
				System.out.println("item:" + item.toString());
				Instance pagework = Instance.builder()

									.build();
				
			});
		};
	}
}
