
package com.mermer.cm.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mermer.cm.entity.Account;
import com.mermer.cm.entity.UpLoadFile;
import com.mermer.cm.service.CommonService;
import com.mermer.cm.util.AppProperties;
import com.mermer.cm.util.CurrentUser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @packageName : com.mermer.cm.controller
 * @fileName : CommonController.java 
 * @author : Mermer 
 * @date : 2022.01.06 
 * @description : 파일업로드 등 공통 기능 요청에 대한 controller
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.06 Mermer 최초 생성
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api/common", produces = MediaTypes.HAL_JSON_VALUE)
@SuppressWarnings("rawtypes")
public class CommonController {
	
	final CommonService commonService; 
	final AppProperties appProperties;
	
	@PostMapping(value = "/img")
	public ResponseEntity uploadImage(@RequestParam(name = "name", required = false) String name,
									  @RequestParam(name = "images") MultipartFile[] imageArr,
	                                  @CurrentUser Account account
	                                  ) throws IOException {
		
		List<UpLoadFile> list = new ArrayList<>();
		Arrays.stream(imageArr).forEach(image -> {
		log.debug("file", image.getName() + ":" + image.getSize());
		
		//TODO 파일업로드 S3 서버로 이전 예정
		String path = appProperties.getPath();
		File folder = new File(path);
		if(!folder.exists())folder.mkdir();
		String fileName = path + (LocalDateTime.now().getLong(ChronoField.MILLI_OF_DAY)) + "developer.jpg";
		FileOutputStream fos;
		BufferedOutputStream bos;
		try {
			fos = new FileOutputStream(fileName);
			bos = new BufferedOutputStream(fos);
			bos.write(image.getBytes());
			fos.close();
			bos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace(); 
		}
		
		UpLoadFile upLoadFile = UpLoadFile.builder()
				.path(path)
				.fileName(fileName)
				.name(name)
				.inster(account)
				.mdfer(account)
				.build()
				;
		list.add(upLoadFile);
		});
		
		

		ResponseEntity result = commonService.createFile(list);
		return result;
	}

}
