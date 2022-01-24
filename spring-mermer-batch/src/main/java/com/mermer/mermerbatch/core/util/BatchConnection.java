/**
 * @packageName : com.mermer.mermerbatch.core.util
 * @fileName : BatchConnection.java 
 * @author : Mermer 
 * @date : 2022.01.17 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.17 Mermer 최초 생성
 */
package com.mermer.mermerbatch.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class BatchConnection {

	static public String getConncetionAndXml(String urlString, boolean flag) {
		
		try {
			URL url = new URL(urlString);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			con.setRequestMethod("GET");
			con.setRequestProperty("Accept-Charset", "UTF-8");
			con.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
			
			
			StringBuilder sb = new StringBuilder();
			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(
						new InputStreamReader(con.getInputStream(), "utf-8"));
				String line;
				
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				br.close();
			} else {
				System.out.println(con.getResponseMessage());
			}

			//작업 끝나고 연결 닫아줌
			con.disconnect();
			
			String xml = sb.toString();
			log.info("flag::" + flag);
			xml = xml.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
			
			return xml; 
			
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("Failed to create UrlResource");
		} catch (IOException e) {
			throw new IllegalArgumentException("Connection Failed");
		}

	}
	
}
