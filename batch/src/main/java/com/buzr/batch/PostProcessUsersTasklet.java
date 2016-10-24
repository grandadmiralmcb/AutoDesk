package com.buzr.batch;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.repeat.RepeatStatus;

import com.buzr.data.Person;


public class PostProcessUsersTasklet implements Tasklet {

	private ItemReader<Person> reader;
	private Person curr;
	
	public PostProcessUsersTasklet(ItemReader<Person> reader)
	{
		this.reader = reader;
		
	}
	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
		
		
	
		while(setNewCurr(reader) != null){
			try {
				printOutPerson(curr);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return RepeatStatus.CONTINUABLE;
			}
		}
		
		
		return RepeatStatus.FINISHED;
	}
	
	private void printOutPerson(Person curr2) {
		System.out.println("Person["+curr2.getId().toString() + "]");
		System.out.println("Name: " + curr2.getFirstName() + " " + curr2.getLastName());
		System.out.println("Email: " + curr2.getEmail());
		System.out.println("Size of secret: " + curr2.getSecret().length());
		
	}
	private Person setNewCurr(ItemReader<Person> reader2) throws UnexpectedInputException, ParseException, NonTransientResourceException, Exception {
		
		this.curr = reader.read();
		return this.curr;
	}

}
