package com.saude.mais.agendamento.Controllers.Hospital;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hospital")
public class HospitalHomeController {

    public HospitalHomeController() {

    }

    @GetMapping
    public String getTemplate(){
        return "hospital_home";
    }
}
