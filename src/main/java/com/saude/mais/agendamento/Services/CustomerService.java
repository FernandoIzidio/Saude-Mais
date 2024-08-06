package com.saude.mais.agendamento.Services;

import com.saude.mais.agendamento.Entities.CustomerEntity;
import com.saude.mais.agendamento.Repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    CustomerRepository customerRepository;


    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public List<CustomerEntity> findAll(){
        return customerRepository.findAll();
    }

    public CustomerEntity findById(Long id){
        return customerRepository.findById(id).get();
    }

//    public List<CustomerEntity> findAllByHospitalId(Long hospitalId){
//        return customerRepository.findAllByHospitalId(hospitalId);
//    }

    public CustomerEntity save(CustomerEntity customerEntity){
        return customerRepository.save(customerEntity);
    }

    public void delete(Long id){
        customerRepository.deleteById(id);
    }


}

