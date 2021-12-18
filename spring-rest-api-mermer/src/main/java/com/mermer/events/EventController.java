package com.mermer.events;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mermer.accounts.Account;
import com.mermer.accounts.CurrentUser;
import com.mermer.common.ErrorsResource;

@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
public class EventController {

	private final EventRepository eventRepository;
	
	private final ModelMapper modelMapper;
	
	private final EventValidator eventValidator;
	
	public EventController(EventRepository eventRepository, ModelMapper modelMapper, EventValidator eventValidator) {
		this.eventRepository = eventRepository;
		this.modelMapper = modelMapper;
		this.eventValidator = eventValidator;
	}
	
	@GetMapping
	public ResponseEntity queryEvents(Pageable pageable, 
									PagedResourcesAssembler<Event> assembler,
									//@AuthenticationPrincipal AccountAdapter currentUser -> 여기서 account를 꺼내는 것과 동일하게 사용 가능
									//@AuthenticationPrincipal(expression = "account") Account account -> CurrentUser 라는  메타어노테이션 생성
									@CurrentUser Account account
									){
		
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		User user = (User)authentication.getPrincipal(); -> @AuthenticationPrincipal 이걸로 바로 주입받을 수 있음
		
		
		Page<Event> page = this.eventRepository.findAll(pageable);
		var pagedResource = assembler.toModel(page, e -> new EventResource(e));
		
		if(account != null) {
			pagedResource.add(linkTo(EventController.class).withRel("create-event"));
		}
		
		pagedResource.add(new Link("/docs/index.html#resources-event-list").withRel("profile"));
		return ResponseEntity.ok(pagedResource);
		
	}

	@GetMapping("/{id}")
	public ResponseEntity getEvent(@PathVariable Integer id,
								   @CurrentUser Account currentUser){
		Optional<Event> optionalEvent = this.eventRepository.findById(id);
		if(optionalEvent.isEmpty()){
			return ResponseEntity.notFound().build();
		}
		Event event = optionalEvent.get();
		EventResource eventResource = new EventResource(event);
		eventResource.add(new Link("/docs/index.html#resources-events-get").withRel("profile"));
		if(currentUser != null && currentUser.equals(event.getManager())) {
			eventResource.add(linkTo(EventController.class).slash(event.getId()).withRel("update-event"));
		}
		return ResponseEntity.ok(eventResource);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity updateEvent(@PathVariable Integer id, 
									  @RequestBody @Valid EventDto eventDto,
									  @CurrentUser Account currentUser,
									  Errors errors){
		
		/* 필수값 검증 */
		if(errors.hasErrors()) {
			System.out.println("==================================");
			System.out.println(errors.toString());
			System.out.println("==================================");
			return badRequest(errors);
		}
		
		/* 비즈니스 로직 검증 */
		eventValidator.validate(eventDto, errors);
		if(errors.hasErrors()) {
			System.out.println("==================================");
			System.out.println(errors.toString());
			System.out.println("==================================");
			return badRequest(errors);
		}
		
		//eventDTo -> event
		Event event = modelMapper.map(eventDto, Event.class);
		
		Optional<Event> optionalEvent = this.eventRepository.findById(id);
		if(optionalEvent.isEmpty()){
			return ResponseEntity.notFound().build();
		}
		
		Event bfEvent = optionalEvent.get();
		if(bfEvent.getManager() == null || !bfEvent.getManager().equals(currentUser)) {//본 작성자가 아닌경우(수정 권한 없는 경우)
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		//수정 데이터 세팅 - modelMapper
		this.modelMapper.map(event, bfEvent);

		//free 상태값 갱신
		bfEvent.update();
		Event savedEvent = this.eventRepository.save(bfEvent);

		EventResource eventResource = new EventResource(savedEvent);
				
		eventResource.add(linkTo(EventController.class).withRel("update-event"));
		eventResource.add(new Link("/docs/index.html#resources-events-update").withRel("profile"));
		
		return ResponseEntity.ok(eventResource);
		
	}
	
	@PostMapping
	public ResponseEntity<Object> createEvent(@RequestBody @Valid EventDto eventDto, 
												Errors errors,
												@CurrentUser Account currentUser) {
		
		//사용자 정보 체크하기
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(errors.hasErrors()) {
			System.out.println("==================================");
			System.out.println(errors.toString());
			System.out.println("==================================");
			return badRequest(errors);
		}
		
		eventValidator.validate(eventDto, errors);
		if(errors.hasErrors()) {
			System.out.println("==================================");
			System.out.println(errors.toString());
			System.out.println("==================================");
			return badRequest(errors);
		}
		
		//eventDTo -> event
		Event event = modelMapper.map(eventDto, Event.class);
		
		//free 상태값 갱신
		event.update();
		event.setManager(currentUser);
		Event newEvent = this.eventRepository.save(event);
		
		
		WebMvcLinkBuilder selfLinkBuilder = linkTo(EventController.class).slash(newEvent.getId());
		URI createdUri = selfLinkBuilder.toUri();
		//event.setId(100);
		EventResource eventResource = new EventResource(event);
		eventResource.add(linkTo(EventController.class).withRel("query-events"));
		eventResource.add(selfLinkBuilder.withRel("update-event"));
		eventResource.add(new Link("/docs/index.html#resources-events-create").withRel("profile"));
		return ResponseEntity.created(createdUri).body(eventResource);
	}


	/* 오류메시지 return */
	private ResponseEntity<Object> badRequest(Errors errors) {
		return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	}
	
}
