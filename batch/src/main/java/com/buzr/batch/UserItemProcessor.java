package com.buzr.batch;

import java.util.Date;
import java.util.UUID;

import org.springframework.batch.item.ItemProcessor;

import com.buzr.data.Person;
import com.buzr.data.PersonRepository;

public class UserItemProcessor implements ItemProcessor<String[], Person> {

	

	private static final int FIRST_NAME_IDX = 0;
	private static final int LAST_NAME_IDX = 1;
	private static final int EMAIL_IDX = 2;
	private static final int SECRET_IDX = 3;

	@Override
	public Person process(String[] arg0) throws Exception {
		Person person = new Person();
		person.setId(UUID.randomUUID());
		person.setDateCreated(new Date());
		
		person.setFirstName(arg0[FIRST_NAME_IDX]);
		person.setLastName(arg0[LAST_NAME_IDX]);
		person.setEmail(arg0[EMAIL_IDX]);
		person.setSecret(arg0[SECRET_IDX]);
		
		return person;
	}

}
