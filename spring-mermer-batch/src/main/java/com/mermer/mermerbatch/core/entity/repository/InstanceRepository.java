
package com.mermer.mermerbatch.core.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mermer.mermerbatch.core.entity.Instance;

/**
 * @packageName : com.mermer.mermerbatch.core.entity.repository
 * @fileName : InstanceRepository.java 
 * @author : Mermer 
 * @date : 2022.01.17 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.17 Mermer 최초 생성
 */
public interface InstanceRepository extends JpaRepository<Instance, Long>{

}
