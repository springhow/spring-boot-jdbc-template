package com.springhow.examples.springboot.jdbctemplate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final TestService testService;

    public UserController(TestService testService) {
        this.testService = testService;
    }
    @GetMapping("/users/{id}")
    UserDto getUser(@PathVariable Integer id) {
        return testService.queryUserById(id);
    }

    @GetMapping("/users")
    List<UserDto> getUsers() {
        return testService.queryFromDatabase(100);
    }
}
