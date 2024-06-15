package com.saude.mais.agendamento.Controllers.Rest;

import com.saude.mais.agendamento.Entities.User.UserEntity;
import com.saude.mais.agendamento.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users/")
public class TestController {


    private UserService userService;

    @Autowired
    public TestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserEntity>> getUser() {
        return ResponseEntity.ok().body(userService.findAll());
    }
}
