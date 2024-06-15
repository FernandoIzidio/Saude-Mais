package com.saude.mais.agendamento.Controllers.Rest;

import com.saude.mais.agendamento.Dtos.LoginDto;
import com.saude.mais.agendamento.Dtos.UserEntityDto;
import com.saude.mais.agendamento.Dtos.TokenResponseDto;
import com.saude.mais.agendamento.Entities.User.UserEntity;
import com.saude.mais.agendamento.Repositories.UserRepository;
import com.saude.mais.agendamento.Security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.ZoneId;

@RestController
@RequestMapping("/auth")
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

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody @Validated LoginDto data) {
        System.out.println("Login attempt for email: " + data.email());

        var authToken = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        System.out.println("authToken: " + authToken);
        Authentication auth = this.authenticationManager.authenticate(authToken);
        System.out.println("auth: " + auth);

        String token = tokenService.generateToken((UserEntity) auth.getPrincipal());

        System.out.println("Token generated: " + token);

        return ResponseEntity.ok(new TokenResponseDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity<UserEntity> register(@RequestBody @Validated UserEntityDto data) {
        System.out.println("Register attempt for email: " + data.email());

        if (this.userRepository.findByEmail(data.email()) != null) {
            System.out.println("Email already exists: " + data.email());
            return ResponseEntity.badRequest().build();
        }

        Instant birhtdate = data.birthDate().atStartOfDay(ZoneId.systemDefault()).toInstant();

        String hash = new BCryptPasswordEncoder().encode(data.password());
        UserEntity user = new UserEntity(data.firstName(), data.lastName(), data.username(), hash, data.email(), data.phone(), data.cpf(), data.role(), birhtdate);

        userRepository.save(user);
        System.out.println("User registered: " + data.email());

        return ResponseEntity.ok().body(user);
    }
}
