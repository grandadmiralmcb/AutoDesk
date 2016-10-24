package com.buzr.batch;

import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.buzr.data.Person;

@Service
public class CassandraItemReader implements ItemReader<Person>{

	@Autowired
	private CassandraOperations cassandraOperations;	
	private int index=0;
	private final Class<Person> clazz = Person.class;
	private String cql;
	private List<Person> personsCache;
	
	public CassandraItemReader() {
		super();
		
	}



	public void setSelect(String cqlSelectStatement)
	{
		//changing the cql statement should reset the count and cache before the next read
		this.cql = cqlSelectStatement;
		index=0;
		this.personsCache=null;
	}

	@Override
	public Person read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		
		Assert.notNull(cql, "You must set the Select Statement before performing this operation");
		Assert.notNull(clazz, "You must set the Person type before performing this operation");
		
		final List<Person> persons;
		//ensure we only call the db once.
		if (this.personsCache == null)
		{
			this.personsCache = cassandraOperations.select(this.cql, clazz );
		}
		persons = personsCache;
		
		if (index < persons.size()) {
			final Person person = persons.get(index);
			index++;
			return person;
		}
		
		return null;
	}

	

}
