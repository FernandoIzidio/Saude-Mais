package com.saude.mais.agendamento.Services;

import com.saude.mais.agendamento.Dtos.UserEntityDto;
import com.saude.mais.agendamento.Entities.HospitalEntity;
import com.saude.mais.agendamento.Entities.User.UserEntity;
import com.saude.mais.agendamento.Entities.User.UserRole;
import com.saude.mais.agendamento.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;

    @Autowired
    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
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

    public UserEntity findByUser(String username) {
        return  userRepository.findByUser(username);
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void save(UserEntityDto user, HospitalEntity hospital) throws Exception {
        if (this.findByEmail(user.email()) != null) {
            throw new Exception("Email already exists: " + user.email());
        }

        Instant birhtdate = user.birthDate().atStartOfDay(ZoneId.systemDefault()).toInstant();
        String hash = new BCryptPasswordEncoder().encode(user.password());

        UserEntity userEntity = createUser(user, birhtdate, hash);

        userEntity.getHospitals().add(hospital);
        userRepository.save(userEntity);
        System.out.println("User registered: " + user.email());
    }

    public UserEntity getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }

    public UserEntity createUser(UserEntityDto user, Instant birthdate, String hash) {
        return new UserEntity(user.firstName(),user.lastName(), user.gender(), user.username(), hash, user.email(), user.phone(), user.cpf(), user.role(), birthdate);
    }

    public UserEntityDto createNullUserDto(UserRole userRole){
        return new UserEntityDto("", "", null, "", "", "", "", "", "", userRole, null);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public void delete(UserEntity user) {
        userRepository.delete(user);
    }
}
