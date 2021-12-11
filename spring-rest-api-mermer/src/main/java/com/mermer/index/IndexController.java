package com.mermer.index;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mermer.events.EventController;

@RestController
public class IndexController {

	
	@GetMapping("/api")
	public ResourceSupport index() {
		var index = new ResourceSupport();
		index.add(ControllerLinkBuilder.linkTo(EventController.class).withRel("events"));
		return index;
	}
	
}
