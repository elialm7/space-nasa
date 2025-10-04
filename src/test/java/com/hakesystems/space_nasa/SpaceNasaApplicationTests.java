package com.hakesystems.space_nasa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hakesystems.space_nasa.persistance.MeteoData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.io.*;
import com.hakesystems.space_nasa.persistance.MeteoDataRepository;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.jca.support.ResourceAdapterFactoryBean;

import java.io.IOException;
import java.nio.charset.Charset;

@SpringBootTest
class SpaceNasaApplicationTests {


	@Autowired
	private  MeteoDataRepository meteoDataRepository;

	private ObjectMapper mapper = new ObjectMapper();

	@Value("classpath:DERLIS_data.json")
	private Resource jsonData;
    @Autowired
    private ObjectMapper objectMapper;


	@Test
	void sqlGeneration() {
		var result = meteoDataRepository.getTableCreationTemplate();
		System.out.println(result);
	}

	@Test
	void insertData() throws IOException {
		var meteoData = new MeteoData();
		meteoData.setData(objectMapper.readTree(jsonData.getContentAsString(Charset.defaultCharset())));
		var result = meteoDataRepository.save(meteoData);
		System.out.println(result);

	}


	@Test
	void getAll(){
		var result = meteoDataRepository.findAll();
		result.forEach(System.out::println);

	}



}
