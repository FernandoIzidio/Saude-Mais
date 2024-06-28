package com.saude.mais.agendamento.Controllers.Admin;

import com.saude.mais.agendamento.Entities.User.UserEntity;
import com.saude.mais.agendamento.Exceptions.SessionExpiredException;
import com.saude.mais.agendamento.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/admin")
public class DashboardController {

    private final UserService userService;

    @Autowired
    public DashboardController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/dashboard")
    public String getDashboard(Model model) {
        try {
            UserEntity authenticatedUser = userService.getAuthenticatedUser();

            if (authenticatedUser == null) {
                throw new SessionExpiredException("Sessão expirada");
            }

            model.addAttribute("user", authenticatedUser);
            model.addAttribute("hospital", authenticatedUser.getHospitals().get(0));
            return "dashboard_admin";
        } catch (SessionExpiredException e) {
            return "redirect:/login?sessionExpired";
        }
    }

}
