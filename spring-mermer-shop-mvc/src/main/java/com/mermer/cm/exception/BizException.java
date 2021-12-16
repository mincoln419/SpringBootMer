
package com.mermer.cm.exception;

import lombok.Getter;

/**
 * @packageName : com.mermer.cm.exception
 * @fileName : BizException.java 
 * @author : Mermer 
 * @date : 2021.12.16 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.16 Mermer 최초 생성
 */
@Getter
public class BizException extends RuntimeException{

	private static final long serialVersionUID = -5491501821967994348L;
	private BizExceptionCode errCode;
	private String detailMessage;
	 
	
	
	public BizException(BizExceptionCode errCode) {
		super();
		this.errCode = errCode;
		this.detailMessage = errCode.getMessage();
	} 
	
	public BizException(BizExceptionCode errCode, String detailMessage) {
		super();
		this.errCode = errCode;
		this.detailMessage = detailMessage;
	} 
}
