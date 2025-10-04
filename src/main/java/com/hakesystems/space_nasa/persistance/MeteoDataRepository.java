package com.hakesystems.space_nasa.persistance;

import com.roelias.crud.CRUD;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

@Repository
public class MeteoDataRepository extends CRUD<MeteoData, Integer> {


    public MeteoDataRepository(Jdbi jdbi) {
        super(jdbi, MeteoData.class, Integer.class);
    }





}
