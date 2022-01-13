
package com.mermer.cm.entity;

import java.time.LocalDateTime;

import com.mermer.cm.util.AccountAdapter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @packageName : com.mermer.cm.entity
 * @fileName : NoticeList.java 
 * @author : Mermer 
 * @date : 2021.12.21 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.21 Mermer 최초 생성
 */
@Getter
@RequiredArgsConstructor
public class NoticeAbstract {

	private final Long id;
	private final String title;
	private final Integer readCnt;
	private final LocalDateTime instDtm;
	private final LocalDateTime mdfDtm;
	private final Long insterId;
	
}
