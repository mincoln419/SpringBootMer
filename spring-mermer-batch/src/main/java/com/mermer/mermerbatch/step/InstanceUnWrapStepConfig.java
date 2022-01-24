
package com.mermer.mermerbatch.step;

import java.sql.SQLException;

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
import com.mermer.mermerbatch.core.entity.dto.InstanceWrapperDto;
import com.mermer.mermerbatch.core.entity.repository.DomainRepository;
import com.mermer.mermerbatch.core.entity.repository.InstanceRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * @packageName : com.mermer.mermerbatch.step
 * @fileName : InstanceUnWrapStep.java 
 * @author : Mermer 
 * @date : 2022.01.23 
 * @description : 처리 instance의 메타데이터 정보를 처리하는 step 
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.23 Mermer 최초 생성
 */
@RequiredArgsConstructor
@Configuration
@EnableBatchProcessing
@Slf4j
public class InstanceUnWrapStepConfig  {
	
	private final StepBuilderFactory stepBuilderFactory;
	private final DomainRepository domainRepository;
	private final InstanceRepository instanceRepository;
	private final LawInstanceAPIResource lawInstanceAPIResource;
	private ExecutionContext context;
	
	

	
	@JobScope
	@Bean("instanceUnWrapStep")
	public Step instanceUnWrapStep(
		    	StaxEventItemReader<InstanceWrapperDto> unWrapReader,
		    	ItemWriter<InstanceWrapperDto> unWrapWriter,
		    	StepExecutionListener stepListener
			) {
		
		return stepBuilderFactory.get("instanceUnWrapStep")
				.listener(stepListener)
				.<InstanceWrapperDto, InstanceWrapperDto>chunk(1)				
				.reader(unWrapReader) 
				.writer(unWrapWriter)
				.build();
				
	}
	
	
	@StepScope
	@Bean
	public StepExecutionListener stepListener() {
		return new StepExecutionListener() {

			@Override
			public void beforeStep(StepExecution stepExecution) {
				context = stepExecution.getJobExecution().getExecutionContext();
			}

			@Override
			public ExitStatus afterStep(StepExecution stepExecution) {
				return ExitStatus.COMPLETED;
			}
					
		};
	}  
	
	@StepScope
	@Bean
	public StaxEventItemReader<InstanceWrapperDto> unWrapReader(
			@Value("#{jobParameters['search']}") String search,
			@Value("#{jobParameters['query']}") String query,
			Unmarshaller articleMarshaller
			) throws SerialException, SQLException{
		 
		Resource resource = lawInstanceAPIResource.getResource(search, query, context);
		
		return new StaxEventItemReaderBuilder<InstanceWrapperDto>()
				.name("InstanceWrapperDto")
				.resource(resource)
				.addFragmentRootElements("법령")
				.unmarshaller(articleMarshaller)//마셜러-> xml 문서 데이터를 객체에 매핑해주는 역할
				.build();
	}
	
	
	@StepScope
	@Bean
	public Jaxb2Marshaller unWrapMarshaller() {		
		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		jaxb2Marshaller.setClassesToBeBound(InstanceWrapperDto.class);
		return jaxb2Marshaller;
	}
	
	@StepScope
	@Bean
	public ItemWriter<InstanceWrapperDto> unWrapWriter(){
		return item -> {
			System.out.println(item);
		};
	}


	
	
}
