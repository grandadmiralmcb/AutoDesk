package com.buzr.api.read;

import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.cassandra.repository.MapId;
import org.springframework.data.cassandra.repository.support.BasicMapId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.buzr.data.Person;import com.buzr.data.PersonRepository;
import com.google.common.collect.Lists;

@SpringBootApplication
@ComponentScan(basePackages="com.buzr")
@RequestMapping("/")
@RestController
public class ReadApiApplication {

	
	@RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
	
	@Autowired
	private PersonRepository repo;
	
	@GetMapping("/Person/{id}")
	public  Person person(@PathVariable UUID id)
	{
		MapId personId = BasicMapId.id("id", id);
		return repo.findOne(personId);
	}
	
	@GetMapping("/Person/findPersonByName")
	public Collection<Person> findPersonByName(@RequestParam String firstName)
	{
		return Lists.newArrayList(repo.findByFirstName(firstName));
	}
	
	@GetMapping("/Person")
	public  Collection<Person> getAll()
	{
		return Lists.newArrayList(repo.findAll());
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ReadApiApplication.class, args);
	}
}
