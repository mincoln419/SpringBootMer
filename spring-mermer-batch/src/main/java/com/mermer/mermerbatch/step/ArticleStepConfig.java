
package com.mermer.mermerbatch.step;

import java.sql.SQLException;
import java.util.Optional;

import javax.sql.rowset.serial.SerialException;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.mermer.mermerbatch.adaptor.LawInstanceAPIResource;
import com.mermer.mermerbatch.core.entity.Domain;
import com.mermer.mermerbatch.core.entity.Instance;
import com.mermer.mermerbatch.core.entity.dto.BasicDto;
import com.mermer.mermerbatch.core.entity.dto.InstanceDto;
import com.mermer.mermerbatch.core.entity.dto.InstanceWrapperDto;
import com.mermer.mermerbatch.core.entity.dto.SubArticleDto;
import com.mermer.mermerbatch.core.entity.embeded.Article;
import com.mermer.mermerbatch.core.entity.embeded.HoArticle;
import com.mermer.mermerbatch.core.entity.embeded.SubArticle;
import com.mermer.mermerbatch.core.entity.repository.DomainRepository;
import com.mermer.mermerbatch.core.entity.repository.InstanceRepository;
import com.mermer.mermerbatch.core.entity.type.ArticleType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class ArticleStepConfig {

	private final StepBuilderFactory stepBuilderFactory;
	private final LawInstanceAPIResource lawInstanceAPIResource;
	private final DomainRepository domainRepository;
	private final InstanceRepository instanceRepository;
	
	private ExecutionContext context;
	
	
	@JobScope // Job이 실행되는 동안에만 step의 객체가 살아있도록
	@Bean("articleStep")
	public Step articleStep(
							StepExecutionListener stepExecutionListener,
						    StaxEventItemReader<InstanceDto> articleReader,
					        ItemWriter<InstanceDto> articleWriter
						   ) {
		return stepBuilderFactory.get("articleStep")
				.listener(stepExecutionListener)
				.<InstanceDto, InstanceDto>chunk(10)
				.reader(articleReader)
				//.processor(null)
				.writer(articleWriter)
				.build();
	}
	
	
	@Bean
	public StepExecutionListener stepExecutionListener() {
		return new StepExecutionListener() {
			
			@Override
			public void beforeStep(StepExecution stepExecution) {
				log.info("[StepExecutionListener] " + stepExecution.getStatus());
				
			}
			
			@Override
			public ExitStatus afterStep(StepExecution stepExecution) {
				log.info("[StepExecutionListener] " + stepExecution.getStatus());
				return stepExecution.getExitStatus();
			}
		};
	}
	

	@StepScope
	@Bean
	public StaxEventItemReader<InstanceDto> articleReader(
			@Value("#{jobParameters['search']}") String search,
			@Value("#{jobParameters['query']}") String query,
			Unmarshaller articleMarshaller,
			StepExecution stepExecution
			) throws SerialException, SQLException{
		 
		Resource resource = lawInstanceAPIResource.getResource(search, query, context);
		
		return new StaxEventItemReaderBuilder<InstanceDto>()
				.name("InstanceDto")
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
	public ItemWriter<InstanceDto> articleWriter(){
		int lawId = 0;
		Optional<Domain> option = domainRepository.findByLawId(lawId);
		Domain domain = option.isPresent()? option.get():null;
		
		return items -> items.forEach(instance -> {
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
		}



	}

