package com.buzr.api.write;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.buzr.data.Person;
import com.buzr.data.PersonRepository;

@RestController
@ComponentScan(basePackages={"com.buzr"})
@SpringBootApplication
public class WriteApiApplication {


	@Autowired
	private PersonRepository repo;
	
	@PostMapping("/Person")
	public Person person(@RequestBody Person person)
	{
		person.setId(UUID.randomUUID());
		person.setDateCreated(new Date());
		return repo.save(person);
		
		
	}

	
	public static void main(String[] args) {
		SpringApplication.run(WriteApiApplication.class, args);
	}
}
