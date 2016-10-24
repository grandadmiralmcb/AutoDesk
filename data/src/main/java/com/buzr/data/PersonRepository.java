package com.buzr.data;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CassandraRepository<Person> {

	@Query("Select * from Person where first_name = ?0")
	Iterable<Person> findByFirstName(String firstName);
}
