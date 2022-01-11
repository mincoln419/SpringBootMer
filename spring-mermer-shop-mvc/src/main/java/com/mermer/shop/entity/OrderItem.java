/**
 * @packageName : com.mermer.shop.entity
 * @fileName : OrderItem.java 
 * @author : Mermer 
 * @date : 2022.01.11 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.11 Mermer 최초 생성
 */
package com.mermer.shop.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.mermer.cm.entity.Account;
import com.mermer.cm.entity.embeded.CommonEmbeded;
import com.mermer.shop.entity.type.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @packageName : com.mermer.shop.entity
 * @fileName : OrderItem.java 
 * @author : Mermer 
 * @date : 2022.01.11 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.11 Mermer 최초 생성
 */
@Entity @NoArgsConstructor @AllArgsConstructor
@Table(name = "SH_TB_ORDER_ITEMS")
@SuperBuilder @Getter @Setter @EqualsAndHashCode(of = "id", callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "SH_SQ_ORDER_ITMES_ID_GENERATOR", sequenceName = "SH_SQ_ORDER_ITEMS_ID", initialValue = 1, allocationSize = 1)
public class OrderItem extends CommonEmbeded{

	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SH_SQ_ORDER_ITMES_ID_GENERATOR")
	private Long id;
	
	@ManyToOne
	private Order order;
	
	@ManyToOne
	private Item item;
	
	private Long price;
	
	private Integer orderCnt;
	
	@ManyToOne
	private Stock stock;
	
}
