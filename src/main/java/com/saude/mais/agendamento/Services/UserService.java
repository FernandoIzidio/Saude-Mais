package com.saude.mais.agendamento.Services;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.saude.mais.agendamento.Dtos.UserDto;
import com.saude.mais.agendamento.Entities.User.UserEntity;
import com.saude.mais.agendamento.Entities.User.UserRole;
import com.saude.mais.agendamento.Repositories.HospitalRepository;
import com.saude.mais.agendamento.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private HospitalRepository hospitalRepository;
    private AddressService addressService;

    @Autowired
    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, HospitalRepository hospitalRepository, AddressService addressService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.hospitalRepository = hospitalRepository;
        this.addressService = addressService;
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

    public UserDetails findByUser(String username) {
        return  userRepository.findByUser(username);
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public BindingResult validate(UserDto userDto, BindingResult bindingResult) {
        if (findByUser(userDto.username()) != null){
            bindingResult.rejectValue("userDto.username", "error.userDto", "Nome de usuário já cadastrado.");
        }

        if (userDto.userRole() != UserRole.ADMIN){
            bindingResult.rejectValue("userDto.userRole", "error.userDto", "Erro ao processar formulário.");
        }


        if (findByEmail(userDto.email()) != null){
            bindingResult.rejectValue("userDto.email", "error.userDto", "Email já cadastrado.");
        }

        if (findByCpf(userDto.cpf()) != null){
            bindingResult.rejectValue("userDto.cpf", "error.userDto", "CPF já cadastrado.");

        }

        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber numberProto = phoneNumberUtil.parse(userDto.phone(), "BR");
            boolean isvalid = phoneNumberUtil.isValidNumber(numberProto) && phoneNumberUtil.getRegionCodeForNumber(numberProto).equals("BR");
            if (!isvalid){
                bindingResult.rejectValue("userDto.phone", "error.userDto", "Número de celular invalido.");
            }

        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }


        return bindingResult;
    }


    @Transactional
    public void save(UserEntity userEntity) {
        userEntity.setPassword(new BCryptPasswordEncoder().encode(userEntity.getPassword()));
        addressService.saveAll(userEntity.getAddresses());
        userRepository.save(userEntity);
    }

    public void saveAll(List<UserEntity> userEntities){
        userRepository.saveAll(userEntities);
    }

    public UserEntity getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserEntity) authentication.getPrincipal();
    }


    public void update(UserEntity userEntity){
        addressService.saveAll(userEntity.getAddresses());
        userRepository.save(userEntity);
    }


    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public void delete(UserEntity user) {
        userRepository.delete(user);
    }
}
