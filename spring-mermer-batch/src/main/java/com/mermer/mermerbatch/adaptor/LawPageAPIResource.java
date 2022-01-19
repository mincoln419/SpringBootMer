
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
 * @fileName : LawDomainAPIResource.java 
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
public class LawPageAPIResource {

	@Autowired
	private PageWorkRepository pageWorkRepository;

	
	private String path;
	private String serviceKey;
	/**
	 * 
	 */
	public LawPageAPIResource(
			@Value("${external.law-domain-api.path}") String path,
			@Value("${external.law-domain-api.service-key}") String serviceKey) {
		this.path = path;
		this.serviceKey = serviceKey;
		System.out.println("====================> LawDomainAPIResource construct");
	
	}
	
	public Resource getResource(String search, String query, StepType step) {

		String urlString = null;
		StringBuilder sb = new StringBuilder();
		StringBuilder fin = new StringBuilder();
		
		if(step.equals(StepType.PAGE)) {
			urlString = String.format("%s&OC=%s&search=%s&query=%s", path, serviceKey
					, search
					, query);
			sb.append(BatchConnection.getConncetionAndXml(urlString, false));
		}else if(step.equals(StepType.DOMAIN)) {
			
			Optional<PageWork> pageInfoOp = pageWorkRepository.findBySearchAndQuery(search, query);
			
			if(pageInfoOp.isEmpty())throw new IllegalArgumentException("No page information");
			
			PageWork pageInfo= pageInfoOp.get(); 
			//페이지만큼 API를 호출하여 처리
			for(int i = 1; i <= pageInfo.getTargetCnt(); i++) {
				urlString = String.format("%s&OC=%s&search=%s&query=%s&page=%d", path, serviceKey
						, search
						, query
						, i
						);
				sb.append(BatchConnection.getConncetionAndXml(urlString, true));
			}
			
			//작업이 끝났으면 끝난 표시 ㄱ
			pageInfo.setFinished(true);
			pageWorkRepository.save(pageInfo);

		}
		log.debug("xml========" + sb.toString());
		fin.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		fin.append("<Root>");
		fin.append(sb.toString());
		fin.append("</Root>");
		
//		log.info("final===" + fin.toString());
		
		Resource result = new ByteArrayResource(fin.toString().getBytes());
		return result;			
	}
	
}
