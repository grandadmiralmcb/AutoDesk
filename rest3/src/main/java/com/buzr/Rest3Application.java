package com.buzr;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@SpringBootApplication
public class Rest3Application extends RepositoryRestConfigurerAdapter{

	@Autowired
	private Collection<Converter> converters;
	
	public static void main(String[] args) {
		SpringApplication.run(Rest3Application.class, args);
	}
	
	@Override
	public void configureConversionService(ConfigurableConversionService csb){
		//DefaultFormattingConversionService csb = new DefaultFormattingConversionService();
		converters.stream().forEach(c -> {
			csb.addConverter(c);
		});
		super.configureConversionService(csb);
	}
}
