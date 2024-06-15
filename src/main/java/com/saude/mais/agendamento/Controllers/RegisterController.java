package com.saude.mais.agendamento.Controllers;

import com.saude.mais.agendamento.Dtos.AddressEntityDto;
import com.saude.mais.agendamento.Dtos.HospitalEntityDto;
import com.saude.mais.agendamento.Dtos.HospitalRegisterDto;
import com.saude.mais.agendamento.Dtos.UserEntityDto;
import com.saude.mais.agendamento.Entities.AddressEntity;
import com.saude.mais.agendamento.Entities.HospitalEntity;
import com.saude.mais.agendamento.Entities.User.UserEntity;
import com.saude.mais.agendamento.Entities.User.UserRole;
import com.saude.mais.agendamento.Services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;
import java.time.ZoneId;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private RegistrationService registrationService;

    @Autowired
    public RegisterController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }


    @GetMapping("/customer")
    public String getCustomerTemplate() {
        return "customer_auth";
    }

    @GetMapping("/hospital")
    public String getHospitalTemplate(Model model) {

        UserEntityDto userEntityDto = new UserEntityDto(null, null, null, null, null, null, null, UserRole.ADMIN, null, null);
        AddressEntityDto address = new AddressEntityDto(null, null, null, null);
        HospitalEntityDto hospitalDto = new HospitalEntityDto(null, null, address, null, null, null, null, null, null, null, null);
        HospitalRegisterDto hospitalRegisterDto = new HospitalRegisterDto(hospitalDto, userEntityDto);
        model.addAttribute("hospitalForm", hospitalRegisterDto);
        return "hospital_auth";
    }

    @PostMapping("/hospital")
    public String registerHospital(@ModelAttribute HospitalRegisterDto hospital) {
        UserEntityDto userEntityDto = hospital.userEntityDto();
        HospitalEntityDto hospitalEntityDto = hospital.hospitalEntityDto();

        Instant licenseExpirationDate = hospitalEntityDto.licenseExpirationDate().atStartOfDay(ZoneId.systemDefault()).toInstant();

        Instant birhtdate = userEntityDto.birthDate().atStartOfDay(ZoneId.systemDefault()).toInstant();

        UserEntity userEntity = new UserEntity(userEntityDto.firstName(), userEntityDto.lastName(), userEntityDto.username(), userEntityDto.password(), userEntityDto.email(), userEntityDto.phone(), userEntityDto.cpf(), userEntityDto.role(), birhtdate);

        AddressEntity address = new AddressEntity(hospitalEntityDto.address().street(), hospitalEntityDto.address().city(), hospitalEntityDto.address().state(), hospitalEntityDto.address().zip());


        HospitalEntity hospitalEntity = new HospitalEntity(hospitalEntityDto.name(), hospitalEntityDto.cnpj(), address, hospitalEntityDto.primaryPhone(), hospitalEntityDto.secondaryPhone(), hospitalEntityDto.email(), hospitalEntityDto.bedCapacity(), hospitalEntityDto.employeeCount(), hospitalEntityDto.doctorCount(), hospitalEntityDto.sanitaryLicenseNumber(), licenseExpirationDate);

        registrationService.registerHospital(hospitalEntity, userEntity);
        System.out.println("Hospital created with success: " + hospitalEntity.toString());
        return "redirect:/";
    }
}
