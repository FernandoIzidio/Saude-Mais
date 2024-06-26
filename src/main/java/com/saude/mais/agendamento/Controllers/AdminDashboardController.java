package com.saude.mais.agendamento.Controllers;

import com.saude.mais.agendamento.Entities.User.UserEntity;
import com.saude.mais.agendamento.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping(value = "/admin")
public class AdminDashboardController {

    private UserService userService;

    public AdminDashboardController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/dashboard")
    public String getDashboard(Model model) {
        UserEntity authenticatedUser = userService.getAuthenticatedUser();
        model.addAttribute("user", authenticatedUser);
        model.addAttribute("hospital", authenticatedUser.getHospitals().get(0));
        return "dashboard_admin";
    }

}
