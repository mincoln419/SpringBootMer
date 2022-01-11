/**
 * @packageName : com.mermer.shop.entity.type
 * @fileName : OrderStatus.java 
 * @author : Mermer 
 * @date : 2022.01.11 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.11 Mermer 최초 생성
 */
package com.mermer.shop.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatus {
		
	ORDER_ACCEPT("주문접수"),
	PAYMENT_COMP("결제완료"),
	DELIV_ORDER("출고지시"),
	DELIV_READY("배송준비"),
	DELIV_START("배송시작"),
	RETRUN_RECIEPT("반품접수"),
	RETRIVAL_ORDER("회수지시"),
	RETRIVAL_STORE("회수입고"),
	RETRIVAL_COMP("회수완료")	
	;
	
		
	private final String description;
}