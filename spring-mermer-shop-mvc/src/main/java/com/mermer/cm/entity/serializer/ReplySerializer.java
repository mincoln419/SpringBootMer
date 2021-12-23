
package com.mermer.cm.entity.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mermer.cm.entity.Reply;

/**
 * @packageName : com.mermer.cm.entity.serializer
 * @fileName : ReplySerializer.java 
 * @author : Mermer 
 * @date : 2021.12.23 
 * @description : reply를 entity 객체로 담기위해 serialize
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.23 Mermer 최초 생성
 */
public class ReplySerializer extends JsonSerializer<Reply> {

	@Override
	public void serialize(Reply reply, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeStartObject();
		gen.writeNumberField("id", reply.getId());
		gen.writeEndObject();
	}

}
