package com.saude.mais.agendamento.Configs;

import com.github.javafaker.Faker;
import com.saude.mais.agendamento.Entities.UserEntity;
import com.saude.mais.agendamento.Repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Profile("test")
public class TestEnvConfig implements CommandLineRunner {

    private final UserRepository userRepository;

    @Autowired
    public TestEnvConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
