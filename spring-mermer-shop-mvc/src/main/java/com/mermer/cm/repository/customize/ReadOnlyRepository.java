
package com.mermer.cm.repository.customize;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.Repository;


/**
 * @packageName : com.mermer.cm.repository
 * @fileName : ReadOnlyRepository.java 
 * @author : Mermer 
 * @date : 2022.01.10 
 * @description : Read 기능만 가능한 메소드를 제공하는 커스터마이징 인터페이스 생성
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.10 Mermer 최초 생성
 */
public interface ReadOnlyRepository<T, ID> extends Repository<T, ID>{

	Optional<?> findById(Long id);
	List<T> findAll();
	List<T> findAllById(Iterable<ID> ids);
	List<T> findAll(Sort sort);
	Page<T> findAll(Pageable pageable);
	
	
}
