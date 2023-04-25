package ua.foxminded.pinchuk.javaspring.universityschedulewebapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.Schedule;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.User;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.controller.ScheduleController;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.ScheduleService;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.UserService;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.exception.UniversityServiceException;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.impl.ScheduleServiceImpl;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.impl.UserServiceImpl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class UniversityScheduleWebAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(UniversityScheduleWebAppApplication.class, args);
    }

}
