package com.mermer.events;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class EventValidator {
	
	public void validate(EventDto eventDto, Errors errors) {
		if (eventDto.getBasePrice() > eventDto.getMaxPrice() && eventDto.getMaxPrice() > 0) {// 비즈니스 룰에 위배
//			errors.rejectValue("basePrice", "wrongValue", "BasePrice is Wrong")
//			errors.rejectValue("maxPrice", "wrongValue", "MaxPrice is Wrong");
			
			errors.reject("Wrong Prices");//글로벌 에러 - 여러 에러 사유가 조합되어있을 경우..
		}

		LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();
		if (endEventDateTime.isBefore(eventDto.getBeginEventDateTime())
				|| endEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())
				|| endEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime())) {

			errors.rejectValue("endEventDateTime", "wrongValue", "EndEventDateTime is Wrong");//필드 에러
		}

	}

}
