package com.saude.mais.agendamento.Controllers;

import com.saude.mais.agendamento.Dtos.CustomerEntityDto;
import com.saude.mais.agendamento.Entities.CustomerEntity;
import com.saude.mais.agendamento.Entities.EmergencyContactsEntity;
import com.saude.mais.agendamento.Entities.HospitalEntity;
import com.saude.mais.agendamento.Entities.User.UserEntity;
import com.saude.mais.agendamento.Entities.User.UserRole;
import com.saude.mais.agendamento.Services.CustomerService;
import com.saude.mais.agendamento.Services.HospitalService;
import com.saude.mais.agendamento.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomersController {

    HospitalService hospitalService;
    UserService userService;
    CustomerService customerService;

    @Autowired
    public CustomersController(HospitalService hospitalService, UserService userService, CustomerService customerService){
        this.hospitalService = hospitalService;
        this.userService = userService;
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerEntityDto customerEntityDto){
        List<EmergencyContactsEntity> emergencyContactsEntityList = CustomerEntityDto.toEmergencyContactsEntities(customerEntityDto.emergencyContacts());

        UserEntity user = customerEntityDto.user().cleanData().toUserEntity();
        HospitalEntity hospital = hospitalService.findById(customerEntityDto.hospitalId());

        if (user.getUserRole() != UserRole.CUSTOMER){
            return ResponseEntity.badRequest().build();
        }

        if (hospital == null) {
            return ResponseEntity.notFound().build();
        }



//        user.setHospital(hospital);
        userService.save(user);

        CustomerEntity customer = new CustomerEntity(user, emergencyContactsEntityList);
        customerService.save(customer);
        return ResponseEntity.ok(customer);
    }
}
