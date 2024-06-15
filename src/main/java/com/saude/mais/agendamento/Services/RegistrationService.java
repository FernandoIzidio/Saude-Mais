package com.saude.mais.agendamento.Services;

import com.saude.mais.agendamento.Entities.AddressEntity;
import com.saude.mais.agendamento.Entities.HospitalEntity;
import com.saude.mais.agendamento.Entities.User.UserEntity;
import com.saude.mais.agendamento.Repositories.AddressRepository;
import com.saude.mais.agendamento.Repositories.HospitalRepository;
import com.saude.mais.agendamento.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegistrationService {

    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private HospitalRepository hospitalRepository;

    public RegistrationService(UserRepository userRepository, AddressRepository addressRepository, HospitalRepository hospitalRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.hospitalRepository = hospitalRepository;
    }

    @Transactional
    public void registerHospital(HospitalEntity hospital, UserEntity user){
        AddressEntity addressEntity = hospital.getAddress();
        addressRepository.save(addressEntity);
        hospitalRepository.save(hospital);
        userRepository.save(user);
    }

    @Transactional
    public void registerHospital(List<HospitalEntity> hospital, List<UserEntity> user){
        List<AddressEntity> addressEntities = new ArrayList<>();
        for(HospitalEntity hospitalEntity : hospital){
            AddressEntity addressEntity = hospitalEntity.getAddress();
            addressEntities.add(addressEntity);
        }
        addressRepository.saveAll(addressEntities);
        hospitalRepository.saveAll(hospital);
        userRepository.saveAll(user);
    }
}
