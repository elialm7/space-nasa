package com.hakesystems.space_nasa.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.hakesystems.space_nasa.persistance.MeteoData;
import com.hakesystems.space_nasa.persistance.MeteoDataRepository;
import com.hakesystems.space_nasa.persistance.ResponseMeteo;
import com.hakesystems.space_nasa.service.ExternalApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/meteo")
public class ApiMeteoData {
    @Autowired
    private MeteoDataRepository meteoDataRepository;
    @Autowired
    private ExternalApi externalApi;
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

        var response = meteoDataRepository.getResponseMeteoIfExist(meteoData);
        if(response.getResponse() == null){
            String fecha = String.valueOf(meteoData.get("fecha"));
            String anio = fecha.substring(0, 4);
            String mes = fecha.substring(5, 7);
            String lat = String.valueOf(meteoData.get("lat"));
            String lon = String.valueOf(meteoData.get("lon"));
            Map<String,String> map = new HashMap<>();
            map.put("anio", anio);
            map.put("mes", mes);
            map.put("lat", lat);
            map.put("lon", lon);
            var data = externalApi.get("https://predictor.roelias.dev/api/prediccion/get", map);

            MeteoData meteoData1 = new MeteoData();
            meteoData1.setData(data);
            meteoDataRepository.save(meteoData1);
            response.setResponse(data);
            var dataR = new ResponseMeteo();
            return ResponseEntity.ok(dataR);

        }else {
            return ResponseEntity.ok(response);
        }
    }






}
