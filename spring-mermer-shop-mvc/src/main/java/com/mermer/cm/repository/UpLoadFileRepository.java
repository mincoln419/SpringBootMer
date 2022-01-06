
package com.mermer.cm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mermer.cm.entity.UpLoadFile;

/**
 * @packageName : com.mermer.cm.repository
 * @fileName : UpLoadFileRepository.java 
 * @author : Mermer 
 * @date : 2022.01.06 
 * @description : 파일 업로드 레포지토리
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.06 Mermer 최초 생성
 */
public interface UpLoadFileRepository extends JpaRepository<UpLoadFile, Long> {

}
