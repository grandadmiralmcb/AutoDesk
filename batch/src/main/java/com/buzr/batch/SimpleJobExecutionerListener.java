package com.buzr.batch;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class SimpleJobExecutionerListener implements JobExecutionListener{

	@Override
	public void afterJob(JobExecution jobExecution) {
		
		    if( jobExecution.getStatus() == BatchStatus.COMPLETED ){
		        System.out.println("Success");
		    }
		    else if(jobExecution.getStatus() == BatchStatus.FAILED){
		       System.out.println("Job id: " + jobExecution.getJobId() + " failed.");
		       jobExecution.getAllFailureExceptions().stream().forEach(failure-> {
		    	   failure.printStackTrace();
		       });
		    }
		
		
	}

	@Override
	public void beforeJob(JobExecution arg0) {
		// TODO Auto-generated method stub
		
	}

}
