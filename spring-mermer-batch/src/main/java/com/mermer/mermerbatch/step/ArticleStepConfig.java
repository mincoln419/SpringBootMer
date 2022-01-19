
package com.mermer.mermerbatch.step;

import java.util.List;

import org.aspectj.weaver.ast.Instanceof;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

import com.mermer.mermerbatch.adaptor.LawDomainAPIResource;
import com.mermer.mermerbatch.adaptor.LawInstanceAPIResource;
import com.mermer.mermerbatch.core.entity.Domain;
import com.mermer.mermerbatch.core.entity.Instance;
import com.mermer.mermerbatch.core.entity.PageWork;
import com.mermer.mermerbatch.core.entity.dto.BasicDto;
import com.mermer.mermerbatch.core.entity.dto.DomainDto;
import com.mermer.mermerbatch.core.entity.dto.InstanceDto;
import com.mermer.mermerbatch.core.entity.dto.InstanceWrapperDto;
import com.mermer.mermerbatch.core.entity.dto.LawSearchDto;
import com.mermer.mermerbatch.core.entity.dto.SubArticleDto;
import com.mermer.mermerbatch.core.entity.embeded.Article;
import com.mermer.mermerbatch.core.entity.embeded.HoArticle;
import com.mermer.mermerbatch.core.entity.embeded.SubArticle;
import com.mermer.mermerbatch.core.entity.repository.DomainRepository;
import com.mermer.mermerbatch.core.entity.repository.InstanceRepository;
import com.mermer.mermerbatch.core.entity.repository.PageWorkRepository;
import com.mermer.mermerbatch.core.entity.type.ArticleType;
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
@Configuration
@EnableBatchProcessing
public class ArticleStepConfig {

	private final StepBuilderFactory stepBuilderFactory;
	private final LawInstanceAPIResource lawInstanceAPIResource;
	private final DomainRepository domainRepository;
	private final InstanceRepository instanceRepository;
	
	
	@JobScope // Job이 실행되는 동안에만 step의 객체가 살아있도록
	@Bean("articleStep")
	public Step articleStep(StaxEventItemReader<InstanceWrapperDto> articleReader,
					     ItemWriter<InstanceWrapperDto> articleWriter
						) {
		return stepBuilderFactory.get("articleStep")
				.<InstanceWrapperDto, InstanceWrapperDto>chunk(10)
				.reader(articleReader)
				.writer(articleWriter)
				.build();
	}
	
	

	@StepScope
	@Bean
	public StaxEventItemReader<InstanceWrapperDto> articleReader(
			@Value("#{jobParameters['search']}") String search,
			@Value("#{jobParameters['query']}") String query,
			Unmarshaller articleMarshaller
			){
		
		Resource resource = lawInstanceAPIResource.getResource(search, query);
		
		return new StaxEventItemReaderBuilder<InstanceWrapperDto>()
				.name("InstanceWrapperDto")
				.resource(resource)
				.addFragmentRootElements("법령")
				.unmarshaller(articleMarshaller)//마셜러-> xml 문서 데이터를 객체에 매핑해주는 역할
				.build();
	}
	
	
	@StepScope
	@Bean
	public Jaxb2Marshaller articleMarshaller() {		
		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		jaxb2Marshaller.setClassesToBeBound(InstanceWrapperDto.class, BasicDto.class, InstanceDto.class, SubArticleDto.class, HoArticle.class);
		return jaxb2Marshaller;
	}
	
	
	@StepScope
	@Bean
	public ItemWriter<InstanceWrapperDto> articleWriter(){
		
		return item -> {
			System.out.println("lawid" + Integer.parseInt(item.get(0).getBasicDto().getLawId()));
			Domain domain = domainRepository.findByLawId(Integer.parseInt(item.get(0).getBasicDto().getLawId())).get();
			
			item.get(0).getArticleWrapperDto().getInstanceDto().forEach(instance -> {
					
					Article article = Article.builder()
									.articleNum(Integer.parseInt(instance.getArticleNum()))
									.articleType(instance.getArticleType().equals("전문") ? ArticleType.PREAMBLE :ArticleType.ARTICLE)
									.content(instance.getArticleContent())
									.build();
					 
					if(instance.getSubArticle() != null) instance.getSubArticle().forEach(sub -> {
						if(sub != null && sub.getSubArticleNum() != null)
							article.getSubArticleList().add(SubArticle.builder()
														.subArticleNum(sub.calArticleNum())
														.content(sub.getSubArticleContent())
														.build()
														);
						if(sub.getHoArticleList() != null)sub.getHoArticleList().forEach(ho -> {
							article.getHoArticleList().add(HoArticle.builder()
														   .subArticleNum(sub.getSubArticleNum() == null ? 0 : sub.calArticleNum())
														   .hoArticleNum(ho.calArticleNum())
														   .content(ho.getHoArticleContent())
														   .build()
														   );
						});
					});
					
					
					Instance inst = Instance.builder()
										.inster(99999999L)
										.mdfer(9999999L)
										.domain(domain)
										.article(article)
										.build();
					instanceRepository.save(inst);
				});
		};
	}
}
