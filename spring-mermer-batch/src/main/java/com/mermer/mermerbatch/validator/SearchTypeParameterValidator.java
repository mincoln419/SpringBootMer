
package com.mermer.mermerbatch.validator;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;

/**
 * @packageName : com.mermer.mermerbatch.job
 * @fileName : SearchTypeParameterValidator.java 
 * @author : Mermer 
 * @date : 2022.01.21 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.21 Mermer 최초 생성
 */
public class SearchTypeParameterValidator implements JobParametersValidator{

	
	private static final String QUERY = "query";
	
	@Override
	public void validate(JobParameters parameters) throws JobParametersInvalidException {
		//TODO 비어있는 경우
		String search = parameters.getString(QUERY);
		if(search == null) {
			throw new JobParametersInvalidException("모든 법령을 조회할 수 없습니다");
		}
		
	}

}
