package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
public class MainPageController {

    @RequestMapping("/")
    String homePage(){
        return "home";
    }


}
