
package com.mermer.cm.exception;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.io.Serializable;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import com.mermer.cm.controller.IndexController;
/**
 * @packageName : com.mermer.cm.exception
 * @fileName : ErrorsResource.java 
 * @author : Mermer 
 * @date : 2021.12.17 
 * @description : 오류발생시 return body resource
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.17 Mermer 최초 생성
 */
public class ErrorsResource extends EntityModel<Errors>{
	 
	/**
	 * badRequest
	 * @param errors
	 * @return
	 * ResponseEntity
	 */
	static public ResponseEntity badRequest(Errors errors) {
		System.out.print(errors.toString());
		return ResponseEntity.badRequest()
				.body(ErrorsResource
						.of(errors)
						.add(linkTo(methodOn(IndexController.class).index()).withRel("index")));
				
	}
	
	
	/**
	 * @method unAuthorizedRequest
	 * @param errors
	 * @return
	 * ResponseEntity
	 * @description 권한이 없는 주소에 접근했을 때 오류 리턴
	 */
	static public ResponseEntity unAuthorizedRequest(Link link) {
		
		Serializable err = new FieldError("accountRole", "authorization", null, true,
				new String[] {"code"}, null, "no author for access");
		return ResponseEntity.status(401)
				.body(ErrorsResource
						.of(err)
						.add(link));
	}
		 
}
