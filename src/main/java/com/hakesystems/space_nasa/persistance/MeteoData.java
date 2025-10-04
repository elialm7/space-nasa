package com.hakesystems.space_nasa.persistance;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.ToString;

import static com.roelias.crud.CRUD.*;
@ToString
@Data
@Table(value = "meteodata", schema = "public", dialect = Dialect.POSTGRESQL)
public class MeteoData {

    @Id
    @Column("id_meteo")
    private Integer id_meteo;

    @JsonColumn
    @Column("data")
    private JsonNode data;

}
