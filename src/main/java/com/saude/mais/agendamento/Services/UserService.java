package com.saude.mais.agendamento.Services;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.saude.mais.agendamento.Dtos.RegisterEntityDto;
import com.saude.mais.agendamento.Entities.HospitalEntity;
import com.saude.mais.agendamento.Entities.User.UserEntity;
import com.saude.mais.agendamento.Entities.User.UserRole;
import com.saude.mais.agendamento.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

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

    public BindingResult validate(RegisterEntityDto registerEntityDto, BindingResult bindingResult) {
        if (findByUser(registerEntityDto.username()) != null){
            bindingResult.rejectValue("registerEntityDto.username", "error.registerEntityDto", "Nome de usuário já cadastrado.");
        }

        if (!registerEntityDto.password().equals(registerEntityDto.password2())) {
            bindingResult.rejectValue("registerEntityDto.password2", "error.registerEntityDto", "Senhas não podem ser diferentes.");
        }

        if (findByEmail(registerEntityDto.email()) != null){
            bindingResult.rejectValue("registerEntityDto.email", "error.registerEntityDto", "Email já cadastrado.");
        }

        if (findByCpf(registerEntityDto.cpf()) != null){
            bindingResult.rejectValue("registerEntityDto.cpf", "error.registerEntityDto", "CPF já cadastrado.");

        }

        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber numberProto = phoneNumberUtil.parse(registerEntityDto.phone(), "BR");
            boolean isvalid = phoneNumberUtil.isValidNumber(numberProto) && phoneNumberUtil.getRegionCodeForNumber(numberProto).equals("BR");
            if (!isvalid){
                bindingResult.rejectValue("registerEntityDto.phone", "error.registerEntityDto", "Número de celular invalido.");
            }

        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }


        return bindingResult;
    }

    public void save(RegisterEntityDto user, HospitalEntity hospital) throws Exception {
        UserEntity userEntity = user.createUserEntity();
        userEntity.getHospitals().add(hospital);
        userRepository.save(userEntity);
        System.out.println("User registered: " + user.email());
    }

    public UserEntity getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUser(username);
    }

    public RegisterEntityDto createNullUserDto(UserRole userRole){
        return new RegisterEntityDto("", "", null, "", "", "", "", "", "", userRole, null);
    }

    public RegisterEntityDto createUserDto(UserEntity user){
        return new RegisterEntityDto(user.getFirstName(), user.getLastName(), user.getGender(), user.getUser(), user.getPassword(), user.getPassword(), user.getEmail(), user.getPhone(), user.getCpf(), user.getRole(), user.getBirthDate());
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public void delete(UserEntity user) {
        userRepository.delete(user);
    }
}
