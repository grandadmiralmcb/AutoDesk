package com.buzr;

import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.cassandra.repository.MapId;
import org.springframework.data.cassandra.repository.support.BasicMapId;
import org.springframework.stereotype.Component;


@Component
public class StringToMapIDConverter implements Converter<String, MapId>{

	@Override
	public MapId convert(String id) {
		
		MapId personId = BasicMapId.id("id", UUID.fromString(id));
		return personId;
	}
}
