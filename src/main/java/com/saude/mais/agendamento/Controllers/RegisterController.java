package com.saude.mais.agendamento.Controllers;

import com.saude.mais.agendamento.Dtos.AddressEntityDto;
import com.saude.mais.agendamento.Dtos.HospitalEntityDto;
import com.saude.mais.agendamento.Dtos.HospitalRegisterDto;
import com.saude.mais.agendamento.Dtos.UserEntityDto;
import com.saude.mais.agendamento.Entities.HospitalEntity;
import com.saude.mais.agendamento.Entities.User.UserRole;
import com.saude.mais.agendamento.Services.AddressService;
import com.saude.mais.agendamento.Services.HospitalService;
import com.saude.mais.agendamento.Services.RegistrationService;
import com.saude.mais.agendamento.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.time.Instant;
import java.time.ZoneId;

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

        UserRole userRole = UserRole.ADMIN;
        UserEntityDto userEntityDto = userService.createNullUserDto(userRole);

        AddressEntityDto address = addressService.createNullAddressDto();

        HospitalEntityDto hospitalDto = hospitalService.createNullHospitalDto(address);

        HospitalRegisterDto hospitalRegisterDto = new HospitalRegisterDto(hospitalDto, userEntityDto);

        model.addAttribute("hospitalForm", hospitalRegisterDto);
        return "hospital_auth";
    }

    @PostMapping
    public RedirectView registerHospital(@ModelAttribute HospitalRegisterDto hospital) {
        UserEntityDto userEntityDto = hospital.userEntityDto();
        HospitalEntityDto hospitalEntityDto = hospital.hospitalEntityDto();
        AddressEntityDto address = hospitalEntityDto.address();

        Instant licenseExpirationDate = hospitalEntityDto.licenseExpirationDate().atStartOfDay(ZoneId.systemDefault()).toInstant();

        String hospitalName = hospitalEntityDto.domainName().trim().replaceAll("\\s+", "").toLowerCase();

        String domain = "www." + hospitalName + ".saude-mais.com.br";
        HospitalEntity hospitalEntity = hospitalService.createHospitalEntity(hospitalEntityDto, domain, addressService.createAddressEntity(address), licenseExpirationDate);

        String url = "http://" + domain + ":8080/";



        String scriptPath = "src/main/java/com/saude/mais/agendamento/Scripts/tenantCreator.sh";
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(scriptPath, hospitalName);

            Process process = processBuilder.start();

            int exitCode = process.waitFor();

            registrationService.registerHospital(hospitalEntity, userEntityDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RedirectView redirectView = new RedirectView(url);
        return redirectView;
    }
}
