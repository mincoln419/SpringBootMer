package com.mermer.events;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class EventValidator {
	
	public void validate(EventDto eventDto, Errors errors) {
		if (eventDto.getBasePrice() > eventDto.getMaxPrice() && eventDto.getMaxPrice() > 0) {// 비즈니스 룰에 위배
			errors.rejectValue("basePrice", "wrongValue", "BasePrice is Wrong");
			errors.rejectValue("maxPrice", "wrongValue", "MaxPrice is Wrong");
		}

		LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();
		if (endEventDateTime.isBefore(eventDto.getBeginEventDateTime())
				|| endEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())
				|| endEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime())) {
			errors.rejectValue("endEventDateTime", "wrongValue", "EndEventDateTime is Wrong");
		}

	}

}
