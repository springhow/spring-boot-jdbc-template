package com.springhow.examples.springboot.jdbctemplate;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TestService {

    private final JdbcTemplate jdbcTemplate;


    public TestService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert() {
        int rowsInserted = jdbcTemplate.update("insert into user_entity (id,first_name,last_name) values (5, 'Marge','Simpson')");
        System.out.println("Number of rows updated = " + rowsInserted);
    }

    /*Inserting multiple records in single statements*/
    public void insertMultiple() {
        int rowsInserted = jdbcTemplate.update("insert into user_entity (id,first_name,last_name) values (5, 'Marge','Simpson'),(6, 'Maggie','Simpson')");
        System.out.println("Number of rows updated = " + rowsInserted);
    }

    /*Demonstrating prepared statements*/
    public void insertUsingPreparedStatement(Integer id, String firstName, String lastName) {
        int rowsInserted = jdbcTemplate.update("insert into user_entity (id,first_name,last_name) values (?,?,?)", ps -> {
            ps.setInt(1, id);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
        });
        System.out.println("Number of rows updated = " + rowsInserted);
    }

    /*To execute multiple insert or update statements in a single Go*/
    public void batchInsert() {
        jdbcTemplate.batchUpdate("insert into user_entity (id,first_name,last_name) values (5, 'Marge','Simpson')", "insert into user_entity (id,first_name,last_name) values (6, 'Maggie','Simpson')");
    }

    /*To batch execute same statement with multiple parameter sets*/
    public void batchInsertWithPreparedStatements() {
        List<Object[]> arr = Arrays.asList(
                new Object[]{5, "Marge", "Simpson"},
                new Object[]{6, "Maggie", "Simpson"},
                new Object[]{7, "Montgomery", "Burns "}
        );
        jdbcTemplate.batchUpdate("insert into user_entity (id,first_name,last_name) values (?,?,?)",arr);
    }

    /*Safe way to insert objects into inserts or updates in batch of specified size*/
    public void batchInsertWithDto(List<UserDto> users) {
        // executing 10 inserts at a time
        jdbcTemplate.batchUpdate("insert into user_entity (id,first_name,last_name) values (?,?,?)", users, 10, (ps, argument) -> {
            ps.setInt(1, argument.getId());
            ps.setString(2, argument.getFirstName());
            ps.setString(3, argument.getLastName());
        });
    }


    public UserDto queryUserById(Integer id) {

        UserDto user = jdbcTemplate.queryForObject("select * from user_entity where id=?", (resultSet, i) -> {
            UserDto userDto = new UserDto();
            userDto.setId(resultSet.getInt("id"));
            userDto.setFirstName(resultSet.getString("first_name"));
            userDto.setLastName(resultSet.getString("last_name"));
            return userDto;
        }, id);
        System.out.println(user);
        return user;
    }

    public List<UserDto> queryFromDatabase(int idLessThan) {

        return jdbcTemplate.query("select id,first_name,last_name from user_entity where id<? ", preparedStatement -> preparedStatement.setInt(1, idLessThan), (resultSet, i) -> {
            UserDto userDto = new UserDto();
            userDto.setId(resultSet.getInt("id"));
            userDto.setFirstName(resultSet.getString("first_name"));
            userDto.setLastName(resultSet.getString("last_name"));
            return userDto;
        });


    }

    public void delete() {
        int rowsDeleted = jdbcTemplate.update("delete from user_entity where id<10");
        System.out.println("Number of rows deleted = " + rowsDeleted);

    }
}
