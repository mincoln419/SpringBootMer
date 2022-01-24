package com.mermer.mermerbatch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mermer.mermerbatch.core.entity.Domain;
import com.mermer.mermerbatch.core.entity.PageWork;
import com.mermer.mermerbatch.core.entity.dto.DomainDto;
import com.mermer.mermerbatch.core.entity.dto.LawSearchDto;
import com.mermer.mermerbatch.core.entity.repository.DomainRepository;
import com.mermer.mermerbatch.core.entity.repository.PageWorkRepository;

/**
 * @packageName : com.mermer.mermerbatch.service
 * @fileName : DomainService.java 
 * @author : Mermer 
 * @date : 2022.01.20 
 * @description : Domain Job의 비즈니스 로직을 구현하는 service
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.20 Mermer 최초 생성
 *
 */
@Service
public class DomainService {

	@Autowired
	private PageWorkRepository pageWorkRepository;
	
	@Autowired
	private DomainRepository domainRepository;

	/**
	 * @param item
	 */
	public void savePageWork(LawSearchDto item, String search, String query) {
		
		PageWork pagework = PageWork.builder()
				.page(Long.parseLong(item.getPage()))
				.total(Long.parseLong(item.getTotal()))
				.rows(Long.parseLong(item.getRows()))
				.search(search)
				.query(query)
				.finished(false)
				.inster(99999999L)
				.mdfer(99999999L)
				.build();
		pagework.updateTargetCnt();
		pageWorkRepository.save(pagework);
		
	}

	/**
	 * @param item
	 */
	public void saveDomainInfo(DomainDto item) {
		Domain domain = Domain.builder()
				.lawId(Integer.parseInt(item.getLawId().trim()))
				.lawMst(Integer.parseInt(item.getLawSerial().trim()))
				.lawName(item.getLawName().trim())
				.inster(99999999L)
				.mdfer(99999999L)
				.build();
		domainRepository.save(domain);
		
	}
	
}
