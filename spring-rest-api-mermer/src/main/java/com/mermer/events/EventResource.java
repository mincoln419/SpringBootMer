package com.mermer.events;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

//public class EventResource extends ResourceSupport{
//	
//	
//	@JsonUnwrapped
//	private Event event;
//	
//	public EventResource(Event event) {
//		this.event = event;
//	}
//
//	public Event getEvent() {
//		return event;
//	}
//}
public class EventResource extends Resource<Event>{

	public EventResource(Event event, Link... links) {
		super(event, links);
		add(ControllerLinkBuilder.linkTo(EventController.class).slash(event.getId()).withSelfRel());
	}
	
}
