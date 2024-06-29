package com.saude.mais.agendamento.Controllers.Admin;

import com.saude.mais.agendamento.Dtos.RegisterEntityDto;
import com.saude.mais.agendamento.Dtos.UserEntityDto;
import com.saude.mais.agendamento.Entities.User.UserEntity;
import com.saude.mais.agendamento.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/dashboard/profile")
public class ProfileController {
    private final UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String profile(Model model) {
        UserEntity user = userService.getAuthenticatedUser();
        UserEntityDto authenticatedUser = user.toUserEntityDto();

        model.addAttribute("user", authenticatedUser.prettyData());
        model.addAttribute("hospital", user.getHospitals().get(0));
        return "profile";
    }
}
