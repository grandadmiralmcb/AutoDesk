
package com.buzr.api.read;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.buzr.data.PersonRepository;

//@RestController
//@RequestMapping("/")
public class PersonController {

	
/*	@Autowired
	private PersonRepository repo;*/
	
	//@GetMapping("/")
	public String index()
	{
		return "The Person Read Repository exposes two endpoints /Person and /Person/{id} where id is an UUID of an object in the store.";
	}
}
