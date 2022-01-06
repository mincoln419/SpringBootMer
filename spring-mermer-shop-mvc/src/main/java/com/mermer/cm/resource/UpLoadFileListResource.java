/**
 * @packageName : com.mermer.cm.entity
 * @fileName : UpLoadFileList.java 
 * @author : Mermer 
 * @date : 2022.01.06 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.06 Mermer 최초 생성
 */
package com.mermer.cm.resource;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import com.mermer.cm.entity.UpLoadFile;

public class UpLoadFileListResource extends CollectionModel<List<EntityModel<UpLoadFile>>> {

}
