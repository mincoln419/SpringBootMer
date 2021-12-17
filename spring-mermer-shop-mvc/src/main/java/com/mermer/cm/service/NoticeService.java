
package com.mermer.cm.service;

import javax.transaction.Transactional;

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
	@Transactional
	public ResponseEntity createNotice(NoticeDto noticeDto) {
		
		//TODO 사용자 정보 가져오기
		
		/*
		 * 데이터 저장  
		 */
		
		
		
		
		//TODO 게시글의 댓글 정보 link return  
		
		
		
		return null;
	}


}
