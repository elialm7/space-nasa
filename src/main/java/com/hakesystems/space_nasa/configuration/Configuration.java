package com.hakesystems.space_nasa.configuration;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.SqlStatements;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public Jdbi createJdbi(DataSource dataSource) {
       var jdbi = Jdbi.create(dataSource);
       jdbi.getConfig().get(SqlStatements.class).setUnusedBindingAllowed(true);
       return jdbi;
    }
}
