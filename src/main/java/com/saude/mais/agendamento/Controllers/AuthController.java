package com.saude.mais.agendamento.Controllers;

import com.saude.mais.agendamento.Dtos.AuthDto;
import com.saude.mais.agendamento.Dtos.AuthResponseDto;
import com.saude.mais.agendamento.Entities.User.UserEntity;
import com.saude.mais.agendamento.Repositories.UserRepository;
import com.saude.mais.agendamento.Security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody @Valid AuthDto data){
        try{

        var authToken = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        Authentication auth = this.authenticationManager.authenticate(authToken);
        UserEntity user = (UserEntity) auth.getPrincipal();
        String token = tokenService.generateToken(user);


        return ResponseEntity.ok(new AuthResponseDto(token, user.toUserDto()));
        } catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
