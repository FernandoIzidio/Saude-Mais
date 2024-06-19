package com.saude.mais.agendamento.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class AdminDashboardController {

    @GetMapping(value = "/dashboard")
    public String getDashboard() {
        return "admin_dashboard";
    }

}
