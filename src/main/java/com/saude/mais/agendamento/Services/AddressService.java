package com.saude.mais.agendamento.Services;

import com.github.javafaker.Address;
import com.saude.mais.agendamento.Dtos.AddressEntityDto;
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

    public AddressEntity createAddressEntity(AddressEntityDto address) {
        return new AddressEntity(address.street(),  address.neighborhood(), address.number(), address.city(), address.state(), address.zip());
    }

    public AddressEntityDto createNullAddressDto() {
        return new AddressEntityDto("", "", "","", "","");
    }

    public void save(AddressEntity address) {
        addressRepository.save(address);
    }
}
