package com.buzr;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.repository.MapId;
import org.springframework.data.cassandra.repository.support.BasicMapId;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.buzr.data.Person;
import com.buzr.data.PersonRepository;

//@RepositoryRestController 
//@RequestMapping (value="/persons")
public class PersonRestRepository
{
	@Autowired
	private PersonRepository personRepo;
	
	
	
	@RequestMapping (method=RequestMethod.GET, value="/{id}")
	public @ResponseBody ResponseEntity<Person> getPerson(@PathVariable UUID id){
		MapId personId = BasicMapId.id("id", id);
		return ResponseEntity.ok(personRepo.findOne(personId));
	}
	
}
