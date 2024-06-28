package com.saude.mais.agendamento.Controllers;

import com.saude.mais.agendamento.Dtos.AddressEntityDto;
import com.saude.mais.agendamento.Dtos.HospitalEntityDto;
import com.saude.mais.agendamento.Dtos.HospitalRegisterDto;
import com.saude.mais.agendamento.Dtos.RegisterEntityDto;
import com.saude.mais.agendamento.Entities.HospitalEntity;
import com.saude.mais.agendamento.Entities.User.Gender;
import com.saude.mais.agendamento.Entities.User.UserRole;
import com.saude.mais.agendamento.Services.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/register")
public class RegisterController {

    private AddressService addressService;
    private UserService userService;
    private RegistrationService registrationService;
    private HospitalService hospitalService;

    @Autowired
    public RegisterController(RegistrationService registrationService, AddressService addressService, UserService userService, HospitalService hospitalService) {
        this.registrationService = registrationService;
        this.addressService = addressService;
        this.userService = userService;
        this.hospitalService = hospitalService;
    }

    @GetMapping
    public String getHospitalTemplate(Model model) {

        RegisterEntityDto registerEntityDto = userService.createNullUserDto(UserRole.ADMIN);
        AddressEntityDto address = addressService.createNullAddressDto();
        HospitalEntityDto hospitalDto = hospitalService.createNullHospitalDto(address);
        HospitalRegisterDto hospitalRegisterDto = new HospitalRegisterDto(registerEntityDto, hospitalDto);

        model.addAttribute("hospitalForm", hospitalRegisterDto);
        model.addAttribute("genders", Gender.values());
        return "register_hospital";
    }


    @PostMapping
    public String registerHospital(@ModelAttribute @Valid HospitalRegisterDto hospital, BindingResult bindingResult, Model model) {
        RegisterEntityDto registerEntityDto = hospital.registerEntityDto().cleanData();
        HospitalEntityDto hospitalEntityDto = hospital.hospitalEntityDto().cleanData();
        AddressEntityDto address = hospitalEntityDto.address().cleanData();

        String hospitalName = hospitalEntityDto.subdomain().trim().replaceAll("\\s+", "").toLowerCase();
        String domain = "www." + hospitalName + ".saude-mais.com.br";

        userService.validate(registerEntityDto, bindingResult);
        hospitalService.validate(hospitalEntityDto, domain, bindingResult);


        if (bindingResult.hasErrors()) {
            model.addAttribute("hospitalForm", hospital);
            model.addAttribute("genders", Gender.values());
            model.addAttribute("bidingResult", bindingResult);
            return "register_hospital";
        }

        HospitalEntity hospitalEntity = hospitalService.createHospitalEntity(hospitalEntityDto, domain, addressService.createAddressEntity(address));

        try {
            registrationService.registerHospital(hospitalEntity, registerEntityDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return "redirect:/login?success";
    }
}
