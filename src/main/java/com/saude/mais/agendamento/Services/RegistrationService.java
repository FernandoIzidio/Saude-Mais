package com.saude.mais.agendamento.Services;

import com.saude.mais.agendamento.Dtos.UserEntityDto;
import com.saude.mais.agendamento.Entities.AddressEntity;
import com.saude.mais.agendamento.Entities.HospitalEntity;
import com.saude.mais.agendamento.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
public class RegistrationService {

    private UserRepository userRepository;
    private AddressService addressService;
    private HospitalService hospitalService;
    private UserService userService;

    public RegistrationService(UserRepository userRepository, AddressService addressService, HospitalService hospitalService, UserService userService) {
        this.userRepository = userRepository;
        this.addressService = addressService;
        this.hospitalService = hospitalService;
        this.userService = userService;
    }

    @Transactional
    public void registerHospital(HospitalEntity hospital, UserEntityDto user) throws Exception {
        AddressEntity addressEntity = hospital.getAddress();
        addressService.save(addressEntity);
        hospitalService.save(hospital);
        userService.save(user, hospital);
    }

}
