package com.saude.mais.agendamento.Controllers;

import com.saude.mais.agendamento.Dtos.UserDto;
import com.saude.mais.agendamento.Entities.User.UserEntity;
import com.saude.mais.agendamento.Entities.User.UserRole;
import com.saude.mais.agendamento.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping
    public ResponseEntity<UserDto> update(@RequestBody UserDto user) {
        UserEntity userEntity = user.toUserEntity();

        if (user.userRole() != UserRole.ADMIN || !Objects.equals(userService.getAuthenticatedUser().getEmail(), user.email()) || user.hospitals().size() > 1){
            return ResponseEntity.badRequest().build();
        }

        userService.update(userEntity);
        return ResponseEntity.ok().body(user);
    }
}
