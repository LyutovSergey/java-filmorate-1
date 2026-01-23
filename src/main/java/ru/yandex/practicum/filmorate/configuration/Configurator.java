package ru.yandex.practicum.filmorate.configuration;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.filmorate.dto.adapters.DurationDeserializer;
import ru.yandex.practicum.filmorate.dto.adapters.DurationSerializer;

import java.text.SimpleDateFormat;
import java.time.Duration;


@Configuration
public class Configurator {

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		objectMapper.setDefaultSetterInfo(JsonSetter.Value.forValueNulls(Nulls.AS_EMPTY));
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		objectMapper.registerModule(new JavaTimeModule()
				.addDeserializer(Duration.class, new DurationDeserializer())
				.addSerializer(Duration.class, new DurationSerializer()
				));

		return objectMapper;
	}
}
