package com.hakesystems.space_nasa.persistance;

import com.roelias.crud.CRUD;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class MeteoDataRepository extends CRUD<MeteoData, Integer> {


    public MeteoDataRepository(Jdbi jdbi) {
        super(jdbi, MeteoData.class, Integer.class);
    }



    public ResponseMeteo getResponseMeteoIfExist(Map<String,Object> meteoData) {

        try {
            String sql = """
                
                    SELECT data as response
                FROM meteodata
                WHERE data->>'fecha' = :fecha
                  AND (data->>'lat')::numeric = :lat
                  AND (data->>'lon')::numeric = :lon;
   

               """;

            return getConfiguredJdbi().withHandle(
                    handle -> {
                        return handle.createQuery(sql)
                                .bind("fecha", String.valueOf(meteoData.get("fecha")))
                                .bind("lat", Float.valueOf(String.valueOf(meteoData.get("lat"))))
                                .bind("lon", Float.valueOf(String.valueOf(meteoData.get("lon"))))
                                .map(getCustomRowMapper(ResponseMeteo.class))
                                .one();
                    }
            );
        }catch (Exception e){
            var data = new ResponseMeteo();
            data.setResponse(null);
            return data;
        }
    }





}
