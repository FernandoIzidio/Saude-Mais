package com.saude.mais.agendamento.Controllers;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.saude.mais.agendamento.Dtos.AddressEntityDto;
import com.saude.mais.agendamento.Dtos.HospitalEntityDto;
import com.saude.mais.agendamento.Dtos.HospitalRegisterDto;
import com.saude.mais.agendamento.Dtos.UserEntityDto;
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

        UserEntityDto userEntityDto = userService.createNullUserDto(UserRole.ADMIN);
        AddressEntityDto address = addressService.createNullAddressDto();
        HospitalEntityDto hospitalDto = hospitalService.createNullHospitalDto(address);
        HospitalRegisterDto hospitalRegisterDto = new HospitalRegisterDto(userEntityDto, hospitalDto);

        model.addAttribute("hospitalForm", hospitalRegisterDto);
        model.addAttribute("genders", Gender.values());
        return "hospital_auth";
    }


    @PostMapping
    public String registerHospital(@ModelAttribute @Valid HospitalRegisterDto hospital, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("hospitalForm", hospital);
            model.addAttribute("genders", Gender.values());
            model.addAttribute("bidingResult", bindingResult);
            return "hospital_auth";
        }

        UserEntityDto userEntityDto = hospital.userEntityDto().cleanData();
        HospitalEntityDto hospitalEntityDto = hospital.hospitalEntityDto().cleanData();
        AddressEntityDto address = hospitalEntityDto.address().cleanData();

        String hospitalName = hospitalEntityDto.domainName().trim().replaceAll("\\s+", "").toLowerCase();
        String domain = "www." + hospitalName + ".saude-mais.com.br";


        if (userService.findByUser(userEntityDto.username()) != null){
            bindingResult.rejectValue("userEntityDto.username", "error.userEntityDto", "Nome de usuário já cadastrado.");
        }

        if (userEntityDto.password() != userEntityDto.password2()) {
            bindingResult.rejectValue("userEntityDto.password2", "error.userEntityDto", "Senhas não podem ser diferentes.");
        }

        if (userService.findByEmail(userEntityDto.email()) != null){
            bindingResult.rejectValue("userEntityDto.email", "error.userEntityDto", "Email já cadastrado.");
        }

        if (userService.findByCpf(userEntityDto.cpf()) != null){
            bindingResult.rejectValue("userEntityDto.cpf", "error.userEntityDto", "CPF já cadastrado.");

        }

        if (hospitalService.findByWebsite(domain) != null){
            bindingResult.rejectValue("hospitalEntityDto.domainName", "error.hospitalEntityDto", "Website já cadastrado.");
        }

        if (hospitalService.findByCnpj(hospitalEntityDto.cnpj()) != null){
            bindingResult.rejectValue("hospitalEntityDto.cnpj", "error.hospitalEntityDto", "CNPJ já cadastrado.");
        }

        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber numberProto = phoneNumberUtil.parse(userEntityDto.phone(), "BR");
             boolean isvalid = phoneNumberUtil.isValidNumber(numberProto) && phoneNumberUtil.getRegionCodeForNumber(numberProto).equals("BR");
             if (!isvalid){
                 bindingResult.rejectValue("userEntityDto.phone", "error.userEntityDto", "Número de celular invalido.");
             }

        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }



        if (bindingResult.hasErrors()) {
            model.addAttribute("hospitalForm", hospital);
            model.addAttribute("genders", Gender.values());
            model.addAttribute("bidingResult", bindingResult);
            return "hospital_auth";
        }





        HospitalEntity hospitalEntity = hospitalService.createHospitalEntity(hospitalEntityDto, domain, addressService.createAddressEntity(address));


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


        return "redirect:/login?success";
    }
}
