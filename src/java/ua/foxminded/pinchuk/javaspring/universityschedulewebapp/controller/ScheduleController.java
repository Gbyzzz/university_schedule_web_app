package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.AppUser;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.ScheduleService;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.UserService;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.exception.UniversityServiceException;

import java.time.LocalDate;

@Controller
@RequestMapping("/schedules")
public class ScheduleController {
    private ScheduleService scheduleService;
    private UserService userService;

    public ScheduleController(ScheduleService scheduleService, UserService userService) {
        this.scheduleService = scheduleService;
        this.userService = userService;
    }

    @GetMapping("")
    String cocktailsPage(Model model){
//        model.addAttribute("all_schedules", scheduleService.getAll());
        return "schedules";
    }

    @GetMapping("/get")
    String getScheduleByUserIdAndDate   (@RequestParam int userId, @RequestParam LocalDate date, @RequestParam String type, Model model) throws Exception {
            model.addAttribute("schedules", scheduleService.getScheduleByUser(userId, date, type));
        return "schedule";
    }
}
