
package com.mermer.mermerbatch.validator;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;

/**
 * @packageName : com.mermer.mermerbatch.validator
 * @fileName : NumberTypeParameter.java 
 * @author : Mermer 
 * @date : 2022.01.16 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.16 Mermer 최초 생성
 */
public class NumberTypeParameterValidator implements JobParametersValidator {

	private static final String SEARCH = "search";
	
	@Override
	public void validate(JobParameters parameters) throws JobParametersInvalidException {

		
		//TODO 비어있는 경우
		
		String search = parameters.getString(SEARCH);
		if(!search.matches("/[0-9]+/g")) {
			throw new JobParametersInvalidException(search + "가 숫자가 아닙니다");
		}
		
		//TODO 길이가 필요할 경우
		

	}

}
