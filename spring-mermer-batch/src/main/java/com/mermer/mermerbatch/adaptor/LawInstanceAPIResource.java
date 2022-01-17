
package com.mermer.mermerbatch.adaptor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.mermer.mermerbatch.core.entity.PageWork;
import com.mermer.mermerbatch.core.entity.repository.PageWorkRepository;
import com.mermer.mermerbatch.core.entity.type.StepType;
import com.mermer.mermerbatch.core.util.BatchConnection;

import lombok.extern.slf4j.Slf4j;

/**
 * @packageName : com.mermer.mermerbatch.adaptor
 * @fileName : LawInstanceAPIResource.java 
 * @author : Mermer 
 * @date : 2022.01.16 
 * @description : 법률 공개 정보 API를 통해 resource 가져오기
 * 1. serviceKey - API 호출을 위해 필요한 인증키 - 법률 공개 서비스에서는 가입한 아이디만 있으면 됨
 *    OC=mincoln419
 * 2. 필수 - target(서비스대상),  type(출력형태 XML), 
 * 3. 선택 - search(1.법령명, 2.본문검색), query(검색질의), display(검색결과 단위개수 최대 100), page(검색결과 페이지)
 * 4. 
 * 
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.16 Mermer 최초 생성
 */
@Slf4j
@Component
public class LawInstanceAPIResource {

	@Value("${external.law-instance-api.path}")
	private String path;
	@Value("${external.law-domain-api.service-key}")
	private String serviceKey;
	
	public Resource getResource(String search, String query) {
		
		
		String urlString = null;
		StringBuilder sb = new StringBuilder();
		
		urlString = String.format("%s&OC=%s&MST=%s", path, serviceKey
				, "223445"
				);
		
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append(BatchConnection.getConncetionAndXml(urlString, false));
		sb.replace(41, 67, "");
		
		System.out.println("xml ==== " + sb.toString().substring(0, 100));
		Resource result = new ByteArrayResource(sb.toString().getBytes());
		return result;			
	}
	
}
