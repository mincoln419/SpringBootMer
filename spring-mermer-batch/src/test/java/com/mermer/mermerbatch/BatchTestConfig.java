
package com.mermer.mermerbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
/**
 * @packageName : com.mermer.mermerbatch.job
 * @fileName : BatchTestConfig.java 
 * @author : Mermer 
 * @date : 2022.01.19 
 * @description : 배치테스트에 필요한 기본적인 환경설정
 * 
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.19 Mermer 최초 생성
 */
@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration
public class BatchTestConfig {

}
