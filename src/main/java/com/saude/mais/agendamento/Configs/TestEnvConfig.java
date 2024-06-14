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


    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        List<UserEntity> users = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            UserEntity userEntity = new UserEntity(null, faker.name().firstName(), faker.internet().password(), faker.internet().safeEmailAddress(), false, false);
            users.add(userEntity);
        }
        userRepository.saveAll(users);
    }
}
