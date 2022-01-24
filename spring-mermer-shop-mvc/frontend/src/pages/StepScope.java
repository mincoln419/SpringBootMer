	@StepScope
	@Bean
	public StaxEventItemReader<DomainDto> domainReader(
			Jaxb2Marshaller domainMarshaller
			){
		log.debug("read-start");
		Resource resource = lawDomainAPIResource.getResource("1", "민법");
		
		log.debug("resource::::::" + resource.toString());
		return new StaxEventItemReaderBuilder<DomainDto>()
				.name("domainDtoReader")
				.resource(resource)
				.addFragmentRootElements("law")
				.unmarshaller(domainMarshaller)//마셜러-> xml 문서 데이터를 객체에 매핑해주는 역할
				.build();
	}