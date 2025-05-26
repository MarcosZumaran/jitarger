package com.vegastore.jitarger.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class AppConfig {
    /**
     * Configura el JdbcTemplate para interactuar con la base de datos
     * @param datasource el datasource que se utiliza para conectarse a la base de datos
     * @return un JdbcTemplate configurado para interactuar con la base de datos
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource datasource) {
        return new JdbcTemplate(datasource);
    }

    /**
     * Configura la fuente de datos para conectarse a la base de datos
     * @return un datasource configurado para conectarse a la base de datos
     */
    @Bean
    public DataSource dataSource(){
        // Se crea un datasource para conectarse a la base de datos
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        // Se configura el driver de la base de datos
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver"); 

        // Se configura la url de la base de datos
        dataSource.setUrl("jdbc:mysql://localhost:3306/jitarger?serverTimezone=UTC"); 
        // Se configura el usuario de la base de datos
        dataSource.setUsername("root"); 
        
        // Se configura la contraseña de la base de datos
        dataSource.setPassword("root"); 
        return dataSource;
    }
}
