
package com.mermer.mermerbatch.adaptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

import io.micrometer.core.ipc.http.HttpSender.Request;
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
public class LawDomainAPIResource {

	@Value("${external.law-domain-api.path}")
	private String path;
	@Value("${external.law-domain-api.service-key}")
	private String serviceKey;
	
	public Resource getResource(String search, String query) {
		String urlString = String.format("%s&OC=%s&search=%s&query=%s", path, serviceKey
				, search
				, query);
		
		log.info("Resource URL = " + urlString);
		
		try {
			URL url = new URL(urlString);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			con.setRequestMethod("GET");
			con.setRequestProperty("Accept-Charset", "UTF-8");
			con.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
			byte[] xml = con.getInputStream().readAllBytes();
			
			Resource result = new ByteArrayResource(xml);
			return result;
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("Failed to create UrlResource");
		} catch (IOException e) {
			throw new IllegalArgumentException("Connection Failed");
		}
	}
	
}
