package com.mermer.events;


import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class EventController {

	@Autowired
	private final EventRepository eventRepository;
	
	public EventController(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}
	
	@PostMapping
	public ResponseEntity<Event> createEvent(@RequestBody Event event) {
		Event newEvent = this.eventRepository.save(event);
		URI createdUri = ControllerLinkBuilder.linkTo(EventController.class)
				.slash("{id}").toUri();
		event.setId(10);
		return ResponseEntity.created(createdUri).body(event);
	}
	
}
