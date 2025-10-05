package com.hakesystems.space_nasa.persistance;

import com.fasterxml.jackson.databind.JsonNode;
import com.roelias.crud.CRUD;
import lombok.Data;

@Data
public class ResponseMeteo {
    @CRUD.JsonColumn
    @CRUD.Column("response")
    private JsonNode response;

}
