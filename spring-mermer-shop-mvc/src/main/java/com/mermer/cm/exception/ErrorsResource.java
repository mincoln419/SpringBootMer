
package com.mermer.cm.exception;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.validation.Errors;

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
	 
	public ErrorsResource(Errors content, Link... links) {
		super(content, links);
		add(linkTo(methodOn(IndexController.class).index()).withRel("index"));
		
	}
	 
}
