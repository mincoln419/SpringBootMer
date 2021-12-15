package com.mermer;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface AccountRepository  extends Neo4jRepository<Account, Long>{
	}
