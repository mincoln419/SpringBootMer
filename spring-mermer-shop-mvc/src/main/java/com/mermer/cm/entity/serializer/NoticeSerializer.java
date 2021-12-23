
package com.mermer.cm.entity.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mermer.cm.entity.Notice;

/**
 * @packageName : com.mermer.cm.entity.serializer
 * @fileName : NoticeSerializer.java 
 * @author : Mermer 
 * @date : 2021.12.23 
 * @description : Notice entity 의존성 entity에서 조회시 serialize
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.23 Mermer 최초 생성
 */
public class NoticeSerializer extends JsonSerializer<Notice>{

	@Override
	public void serialize(Notice notice, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeStartObject();
		gen.writeNumberField("id", notice.getId());
		gen.writeEndObject();
		
	}

}
