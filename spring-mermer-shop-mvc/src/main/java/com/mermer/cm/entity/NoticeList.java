
package com.mermer.cm.entity;

import java.time.LocalDateTime;

import com.mermer.cm.entity.type.UseYn;
import com.mermer.cm.util.AccountAdapter;

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
public interface NoticeList {

	Long getId();
	String getTitle();
	Integer getReadCnt();
	LocalDateTime getInstDtm();
	LocalDateTime getMdfDtm();
	Long getInsterId();
}
