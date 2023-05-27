package ua.foxminded.pinchuk.javaspring.universityschedulewebapp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.controller.AdminController;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.repository.CourseRepository;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.repository.ScheduleRepository;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.repository.UserRepository;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.initializer.Postgres;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.UserService;


@ActiveProfiles("test")
@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
//@RunWith(SpringRunner.class)
//@ComponentScan(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
//        classes = {CourseRepository.class, ScheduleRepository.class, UserRepository.class,
//        UserService.class, AdminController.class}))
//@SpringBootTest
//@AutoConfigureMockMvc
@ContextConfiguration(initializers = {
        Postgres.Initializer.class
})
public abstract class IntegrationTestBase {


    @BeforeAll
    static void init(){
        Postgres.container.start();
    }

}
