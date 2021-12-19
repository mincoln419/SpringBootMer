
package com.mermer.cm.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

/**
 * @packageName : com.mermer.cm.util
 * @fileName : CurrentUser.java 
 * @author : Mermer 
 * @date : 2021.12.19 
 * @description : currentUser customize 어노테이션
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.19 Mermer 최초 생성
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : account" )
public @interface CurrentUser {

}
