
package com.mermer.cm.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.AbstractErrors;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import lombok.Getter;

/**
 * @packageName : com.mermer.cm.exception
 * @fileName : ErrorsIpml.java 
 * @author : Mermer 
 * @date : 2021.12.20 
 * @description : parameter에 error가 없을 때 return 용 errors handling
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.20 Mermer 최초 생성
 */
@Getter
public class ErrorsIpml extends AbstractErrors {

	List<FieldError> fieldErrors;
	String field;
	String objectName;
	/**
	 * 
	 */
	public ErrorsIpml(String path) {
		fieldErrors = new ArrayList<>();
		doSetNestedPath(path);
	}
	
	@Override
	protected void doSetNestedPath(String nestedPath) {
		super.doSetNestedPath(nestedPath);
	}

	@Override
	public String getObjectName() {
		return fieldErrors.get(0).getObjectName();
	}

	@Override
	public void reject(String errorCode, String defaultMessage) {
			fieldErrors.add(new FieldError(errorCode, errorCode, defaultMessage));
	}

	@Override
	public void rejectValue(String field, String errorCode,  String defaultMessage) {
		fieldErrors.add(new FieldError(field, errorCode, defaultMessage));
	}

	@Override
	public void addAllErrors(Errors errors) {
				
	}

	@Override
	public List<ObjectError> getGlobalErrors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FieldError> getFieldErrors() {
		
		return fieldErrors;
	}

	@Override
	public Object getFieldValue(String field) {
		return fieldErrors.get(0).getField();
	}

	@Override
	public void reject(String errorCode, Object[] errorArgs, String defaultMessage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rejectValue(String field, String errorCode, Object[] errorArgs, String defaultMessage) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean hasErrors() {
		return fieldErrors.size() > 0;
	}
	
	
}
