package com.mermer.sample;

import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class SampleController {

	@GetMapping("/hello")
	public Resource<Sample> hello() {
		Sample hello = new Sample();
		hello.setPrefix("Hey,");
		hello.setName("Mermer");
		
		
		Resource<Sample> sampleResource = new Resource<Sample>(hello);
		sampleResource.add(linkTo(methodOn(SampleController.class).hello()).withSelfRel());
		
		return sampleResource;
	}
}
