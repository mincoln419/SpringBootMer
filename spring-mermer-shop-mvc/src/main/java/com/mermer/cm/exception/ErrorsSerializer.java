
package com.mermer.cm.exception;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.validation.Errors;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @packageName : com.mermer.cm.exception
 * @fileName : ErrorsSerializer.java 
 * @author : Mermer 
 * @date : 2021.12.17 
 * @description : 오류메시지 리턴시 시리얼라이징 시켜주는 Aspect
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.17 Mermer 최초 생성
 */
@JsonComponent
public class ErrorsSerializer extends JsonSerializer<Errors>{

	@Override
	public void serialize(Errors errors, JsonGenerator gen, SerializerProvider provider) throws IOException {
		
		gen.writeFieldName("errors");//스프링 부트 2.3부터 jackson 라이브러리가 더이상 startArray 부터 만드는 걸 허용하지 않음
		gen.writeStartArray();
		/*
		 * Global Error json serialization
		 */
		errors.getFieldErrors().stream().forEachOrdered(e -> {
			try {
				gen.writeStartObject();
				gen.writeStringField("field", e.getField());
				gen.writeStringField("objectName", e.getObjectName());
				gen.writeStringField("code", e.getCode());
				gen.writeStringField("defaultMessage", e.getDefaultMessage());

				Object rejectedValue = e.getRejectedValue();
				if (rejectedValue != null) {
					gen.writeStringField("rejectedValue", rejectedValue.toString());
				}

				gen.writeEndObject();

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		/*
		 * Global Error json serialization
		 */
		errors.getGlobalErrors().forEach(e -> {
			try {
				gen.writeStartObject();

				gen.writeStringField("objectName", e.getObjectName());
				gen.writeStringField("code", e.getCode());
				gen.writeStringField("defaultMessage", e.getDefaultMessage());

				gen.writeEndObject();

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		gen.writeEndArray();
		
	}
}
