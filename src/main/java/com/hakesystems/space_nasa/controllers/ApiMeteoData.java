package com.hakesystems.space_nasa.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.hakesystems.space_nasa.persistance.MeteoData;
import com.hakesystems.space_nasa.persistance.MeteoDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/meteo")
public class ApiMeteoData {
    @Autowired
    private MeteoDataRepository meteoDataRepository;
    @PostMapping
    public ResponseEntity<?> getData(@RequestBody JsonNode meteoData) {
        var meteadoData = new MeteoData();
        meteadoData.setData(meteoData);
        return ResponseEntity.ok(
                Map.of("return", meteoDataRepository.save(meteadoData))
        );
    }

    @GetMapping
    public ResponseEntity<?> getPredictionWithCache(@RequestParam Map<String,Object> meteoData) {



        return ResponseEntity.ok(meteoDataRepository.getResponseMeteoIfExist(meteoData));
    }






}
