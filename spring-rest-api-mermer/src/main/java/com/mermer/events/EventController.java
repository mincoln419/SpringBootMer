package com.mermer.events;


import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mermer.common.ErrorsResource;

@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
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
	public ResponseEntity queryEvents(Pageable pageable, PagedResourcesAssembler<Event> assembler){
		Page<Event> page = this.eventRepository.findAll(pageable);
		var pagedResource = assembler.toResource(page, e -> new EventResource(e));
		pagedResource.add(new Link("/docs/index.html#resources-event-list").withRel("profile"));
		return ResponseEntity.ok(pagedResource);
		
	}

	@GetMapping("/{id}")
	public ResponseEntity getEvent(@PathVariable Integer id){
		Optional<Event> optionalEvent = this.eventRepository.findById(id);
		if(optionalEvent.isEmpty()){
			return ResponseEntity.notFound().build();
		}
		Event event = optionalEvent.get();
		EventResource eventResource = new EventResource(event);
		eventResource.add(new Link("/docs/index.html#resources-events-get").withRel("profile"));
		return ResponseEntity.ok(eventResource);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity updateEvent(@PathVariable Integer id, @RequestBody @Valid EventDto eventDto, Errors errors){
		
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
		//수정 데이터 세팅 - modelMapper
		this.modelMapper.map(eventDto, bfEvent);

		//free 상태값 갱신
		bfEvent.update();
		Event savedEvent = this.eventRepository.save(bfEvent);

		EventResource eventResource = new EventResource(savedEvent);
				
		eventResource.add(ControllerLinkBuilder.linkTo(EventController.class).withRel("update-event"));
		eventResource.add(new Link("/docs/index.html#resources-events-update").withRel("profile"));
		
		return ResponseEntity.ok(eventResource);
		
	}
	
	@PostMapping
	public ResponseEntity<Object> createEvent(@RequestBody @Valid EventDto eventDto, Errors errors) {
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
		
		Event newEvent = this.eventRepository.save(event);
		ControllerLinkBuilder selfLinkBuilder = ControllerLinkBuilder.linkTo(EventController.class).slash(newEvent.getId());
		URI createdUri = selfLinkBuilder.toUri();
		//event.setId(100);
		EventResource eventResource = new EventResource(event);
		eventResource.add(ControllerLinkBuilder.linkTo(EventController.class).withRel("query-events"));
		eventResource.add(selfLinkBuilder.withRel("update-event"));
		eventResource.add(new Link("/docs/index.html#resources-events-create").withRel("profile"));
		return ResponseEntity.created(createdUri).body(eventResource);
	}


	/* 오류메시지 return */
	private ResponseEntity<Object> badRequest(Errors errors) {
		return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	}
	
}
