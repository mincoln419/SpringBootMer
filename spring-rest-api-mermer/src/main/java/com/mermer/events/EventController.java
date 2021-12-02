package com.mermer.events;


import java.net.URI;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	
	@PostMapping
	public ResponseEntity<Object> createEvent(@RequestBody @Valid EventDto eventDto, Errors errors) {
		if(errors.hasErrors()) {
			System.out.println("==================================");
			System.out.println(errors.toString());
			System.out.println("==================================");
			return ResponseEntity.badRequest().body(errors);
		}
		
		eventValidator.validate(eventDto, errors);
		if(errors.hasErrors()) {
			System.out.println("==================================");
			System.out.println(errors.toString());
			System.out.println("==================================");
			return ResponseEntity.badRequest().body(errors);
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
		return ResponseEntity.created(createdUri).body(eventResource);
	}
	
}
