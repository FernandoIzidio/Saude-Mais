package com.saude.mais.agendamento.Controllers.Hospital;

import com.saude.mais.agendamento.Dtos.AddressEntityDto;
import com.saude.mais.agendamento.Dtos.HospitalEntityDto;
import com.saude.mais.agendamento.Dtos.HospitalRegisterDto;
import com.saude.mais.agendamento.Dtos.RegisterEntityDto;
import com.saude.mais.agendamento.Entities.BrazilianStates;
import com.saude.mais.agendamento.Entities.HospitalEntity;
import com.saude.mais.agendamento.Entities.User.Gender;
import com.saude.mais.agendamento.Entities.User.UserEntity;
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
@RequestMapping(value = "/hospital/register")
public class HospitalRegisterController {

    private AddressService addressService;
    private UserService userService;
    private RegistrationService registrationService;
    private HospitalService hospitalService;

    @Autowired
    public HospitalRegisterController(RegistrationService registrationService, AddressService addressService, UserService userService, HospitalService hospitalService) {
        this.registrationService = registrationService;
        this.addressService = addressService;
        this.userService = userService;
        this.hospitalService = hospitalService;
    }

    @GetMapping
    public String getTemplate(Model model) {

        RegisterEntityDto registerEntityDto = RegisterEntityDto.createNullRegisterEntityDto(UserRole.ADMIN);
        HospitalEntityDto hospitalDto = HospitalEntityDto.createNullHospitalEntityDto();
        HospitalRegisterDto hospitalRegisterDto = new HospitalRegisterDto(registerEntityDto, hospitalDto);


        model.addAttribute("hospitalForm", hospitalRegisterDto);
        model.addAttribute("genders", Gender.values());
        model.addAttribute("states", BrazilianStates.values());
        return "hospital_register";
    }


    @PostMapping
    public String registerHospital(@ModelAttribute @Valid HospitalRegisterDto hospital, BindingResult bindingResult, Model model) {
        RegisterEntityDto registerEntityDto = hospital.registerEntityDto().cleanData();
        HospitalEntityDto hospitalEntityDto = hospital.hospitalEntityDto().cleanData();
        AddressEntityDto address = hospitalEntityDto.address().cleanData();

        userService.validate(registerEntityDto, bindingResult);
        hospitalService.validate(hospitalEntityDto, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("hospitalForm", hospital);
            model.addAttribute("genders", Gender.values());
            model.addAttribute("bidingResult", bindingResult);
            model.addAttribute("states", BrazilianStates.values());
            return "hospital_register";
        }

        HospitalEntity hospitalEntity = hospitalEntityDto.toHospitalEntity();
        UserEntity userEntity =  registerEntityDto.toUserEntity();


        registrationService.registerHospital(hospitalEntity, userEntity);


        return "redirect:/login?success";
    }
}
