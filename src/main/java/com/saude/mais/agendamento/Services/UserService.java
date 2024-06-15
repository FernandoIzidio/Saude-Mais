package com.saude.mais.agendamento.Services;

import com.saude.mais.agendamento.Entities.User.UserEntity;
import com.saude.mais.agendamento.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public UserEntity findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserEntity findByCpf(String cpf) {
        return userRepository.findByCpf(cpf);
    }

    public UserDetails findByEmail(String email) { return userRepository.findByEmail(email); }

    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public void delete(UserEntity user) {
        userRepository.delete(user);
    }
}
