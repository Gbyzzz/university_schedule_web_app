package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
public class MainPageController implements ErrorController {

    @RequestMapping("/")
    String homePage(){
        return "home";
    }

    @RequestMapping("/error")
    String error(String errorMessage, Model model){
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }


}
