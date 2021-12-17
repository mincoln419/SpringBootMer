
package com.mermer.cm.service;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mermer.cm.entity.dto.NoticeDto;
import com.mermer.cm.repository.AccountRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @packageName : com.mermer.cm.service
 * @fileName : NoticeService.java 
 * @author : Mermer 
 * @date : 2021.12.17 
 * @description : Notice Service
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.17 Mermer 최초 생성
 */
@Service
@RequiredArgsConstructor //=> 자동으로 AccountRepository를 injection 처리
@Slf4j
public class NoticeService {

	/**
	 * @methond createNotice
	 * @param noticeDto
	 * @return
	 * ResponseEntity
	 * @description 
	 */
	public ResponseEntity createNotice(NoticeDto noticeDto) {
		
		
		return null;
	}


}
