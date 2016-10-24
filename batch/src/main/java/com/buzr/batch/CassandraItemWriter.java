package com.buzr.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buzr.data.Person;
import com.buzr.data.PersonRepository;

@Service
public class CassandraItemWriter implements ItemWriter<Person>{

	@Autowired
	private PersonRepository repo;
	
	@Override
	public void write(List<? extends Person> arg0) throws Exception {
		
		
		repo.save(arg0);
	}
	
	

}
