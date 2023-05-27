package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    String getAllUsers(Model model){
        model.addAttribute("users", userService.findAll());
        return "users";
    }
}
