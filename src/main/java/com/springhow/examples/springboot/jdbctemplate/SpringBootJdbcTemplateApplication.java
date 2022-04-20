package com.springhow.examples.springboot.jdbctemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication
public class SpringBootJdbcTemplateApplication {

    @Bean
    JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        jdbcTemplate.setQueryTimeout(20); //20 seconds
        jdbcTemplate.setFetchSize(10);  //fetch 10 rows at a time
        return jdbcTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootJdbcTemplateApplication.class, args);
    }

}
