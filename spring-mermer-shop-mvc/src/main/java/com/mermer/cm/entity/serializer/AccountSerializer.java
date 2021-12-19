package com.mermer.cm.entity.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mermer.cm.entity.Account;

/**
 * @packageName : com.mermer.cm.entity.serializer
 * @fileName : AccountSerializer.java 
 * @author : Mermer 
 * @date : 2021.12.17 
 * @description : Account 객체를 타 entity에서 사용할 때의 Serializer
 * 				  계정정보중 예민한 부분을 없애고 보여줄 수 있음
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.17 Mermer 최초 생성
 */
public class AccountSerializer extends JsonSerializer<Account>{
		@Override
		public void serialize(Account account, JsonGenerator gen, SerializerProvider serializers) throws IOException {
			gen.writeStartObject();
			gen.writeNumberField("accountId", account.getId());
			gen.writeEndObject();
		}
}
