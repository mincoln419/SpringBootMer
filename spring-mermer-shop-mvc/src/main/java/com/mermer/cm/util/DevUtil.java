/**
 * 
 */
package com.mermer.cm.util;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.hateoas.server.core.LinkBuilderSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import com.mermer.cm.controller.NoticeController;

import lombok.experimental.UtilityClass;

/**
 * @author N
 *
 */
@UtilityClass
public final class DevUtil {

	public static void printlong() {
			
	}

	/**
	 * @param <T>
	 * @method getClassLink
	 * @param accountId
	 * @return
	 * WebMvcLinkBuilder
	 * @description 
	 */
	static public <T> WebMvcLinkBuilder getClassLink(Class<T> classname, Long noticeId) {

		return linkTo(classname.getClass()).slash(noticeId);
	}

	/**
	 * @param <T>
	 * @method getClassLink
	 * @param class1
	 * @param noticeId
	 * @param string
	 * @return
	 * LinkBuilderSupport<WebMvcLinkBuilder>
	 * @description 
	 */
	public static <T> WebMvcLinkBuilder getClassLink(Class<T> classname, Long noticeId,
			String string) {
		return linkTo(classname.getClass()).slash(noticeId).slash(string);
	}
	
}
