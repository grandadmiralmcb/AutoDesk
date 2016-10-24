package com.buzr.batch;

import java.util.UUID;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.ArrayFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import com.buzr.data.Person;

@SpringBootApplication( scanBasePackages={"com.buzr"})
@EnableBatchProcessing
public class BatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchApplication.class, args);
	}
	
	@Value("${batchSource}")
	private String batchSource;

	
	  @Bean  
	  @Qualifier("post-process") public Tasklet postProcessorTasklet(CassandraItemReader reader) { return
	  new PostProcessUsersTasklet(reader); 
	  }
	 

	@Bean
	public ItemReader<Person> dbReader(CassandraItemReader reader) {

		reader.setSelect("Select * from person");
		return reader;
	}
	

	@Bean
	public ItemReader<String[]> reader() {
		FlatFileItemReader<String[]> reader = new FlatFileItemReader<String[]>();
		reader.setResource(new ClassPathResource(batchSource));
		reader.setLineMapper(new DefaultLineMapper<String[]>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[] { "firstName", "lastName", "secret", "email" });
					}
				});
				setFieldSetMapper(new ArrayFieldSetMapper());

			}
		});
		// log.info("Reader Initialized");
		return reader;
	}

	@Bean
	public ItemProcessor<String[], Person> processor() {
		return new UserItemProcessor();
	}
	


	@Bean
	public Job importUserJob(JobBuilderFactory jobs,  @Qualifier("s1") Step s1,
			@Qualifier("s2") Step s2, JobExecutionListener listener) {
		return jobs.get(UUID.randomUUID().toString()).incrementer(new RunIdIncrementer()).listener(listener).flow(s1)
				.next(s2).end().build();
	}

	

	@Bean
	@Qualifier("s1")
	public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<String[]> reader, ItemWriter<Person> writer,
			ItemProcessor<String[], Person> processor) {
		return stepBuilderFactory.get("step1").<String[], Person>chunk(1).reader(reader).processor(processor)
				.writer(writer).build();
	}

	@Bean
	@Qualifier("s2")
	public Step step2(StepBuilderFactory stepBuilderFactory, @Qualifier("post-process") Tasklet postProcessTasklet) {
		return stepBuilderFactory.get("step2").tasklet(postProcessTasklet).build();
	}
	
	
}
