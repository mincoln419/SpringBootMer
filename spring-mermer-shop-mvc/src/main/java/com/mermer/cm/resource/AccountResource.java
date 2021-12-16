package com.mermer.cm.resource;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.Optional;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import com.mermer.cm.controller.CMACController;
import com.mermer.cm.entity.TB_CMAC_ACOUNT;

public class AccountResource extends EntityModel<Optional>{

	public AccountResource(Optional<TB_CMAC_ACOUNT> result, Link ...links ) {
		
		 of(result, linkTo(CMACController.class).slash(String.valueOf(result.get().getAccountId())).withSelfRel());
	}

}
