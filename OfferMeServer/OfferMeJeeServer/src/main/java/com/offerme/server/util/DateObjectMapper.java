package com.offerme.server.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;

public class DateObjectMapper extends ObjectMapper {
	
	public DateObjectMapper(){  
		
		CustomSerializerFactory factory = new CustomSerializerFactory();  
		factory.addGenericMapping(Date.class, new JsonSerializer<Date>(){
			@Override
			public void serialize(Date value, JsonGenerator generator,
					SerializerProvider provider) throws IOException, JsonProcessingException {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
				generator.writeString(sdf.format(value));
			
		}});
		this.setSerializerFactory(factory);  
	} 

}
