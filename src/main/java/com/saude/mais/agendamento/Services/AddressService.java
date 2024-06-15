package com.saude.mais.agendamento.Services;

import com.github.javafaker.Address;
import com.saude.mais.agendamento.Entities.AddressEntity;
import com.saude.mais.agendamento.Repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }


    public void save(AddressEntity address) {
        addressRepository.save(address);
    }
}
