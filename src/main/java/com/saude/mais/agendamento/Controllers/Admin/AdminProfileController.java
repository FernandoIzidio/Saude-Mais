package com.saude.mais.agendamento.Controllers.Admin;

import com.saude.mais.agendamento.Dtos.UserEntityDto;
import com.saude.mais.agendamento.Entities.User.UserEntity;
import com.saude.mais.agendamento.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/dashboard/profile")
public class AdminProfileController {
    private final UserService userService;

    @Autowired
    public AdminProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String profile(Model model) {
        UserEntity user = userService.getAuthenticatedUser();
        UserEntityDto authenticatedUser = user.toUserEntityDto();
        System.out.println(authenticatedUser);

        model.addAttribute("user", authenticatedUser.prettyData());
        return "admin_profile";
    }

    @PostMapping
    public String updateProfile(@ModelAttribute UserEntityDto userDto, BindingResult bindingResult, Model model) {
        UserEntity authenticatedUser = userService.getAuthenticatedUser();




        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userDto);
            model.addAttribute("bindingResult", bindingResult);
            return "admin_profile";
        }

    authenticatedUser.setFirstName(userDto.firstName());
        authenticatedUser.setLastName(userDto.lastName());
    authenticatedUser.setGender(userDto.gender());
    authenticatedUser.setUser(userDto.username());
    authenticatedUser.setPhone(userDto.phone());
    authenticatedUser.setBirthdate(userDto.birthdate());
    authenticatedUser.cleanData();
    userService.save(authenticatedUser);
    model.addAttribute("user", authenticatedUser.toUserEntityDto());
    return "admin_profile";
    }
}
