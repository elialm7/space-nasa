package com.hakesystems.space_nasa.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component
public class ExternalApi {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ExternalApi() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public JsonNode get(String baseUrl, Map<String, String> queryParams) {
        // Construimos la URL con parámetros
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl);
        if (queryParams != null) {
            queryParams.forEach(builder::queryParam);
        }
        String finalUrl = builder.toUriString();

        // Ejecutamos la request GET
        ResponseEntity<String> response = restTemplate.getForEntity(finalUrl, String.class);

        try {
            // Parseamos la respuesta a JsonNode
            return objectMapper.readTree(response.getBody());
        } catch (Exception e) {
            throw new RuntimeException("Error parsing JSON response from " + finalUrl, e);
        }
    }
}
