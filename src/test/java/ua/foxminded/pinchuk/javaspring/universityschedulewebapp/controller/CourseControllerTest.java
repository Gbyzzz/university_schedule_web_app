package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.Source;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.Course;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.CourseService;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.utils.CustomWithMockUser;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(CourseController.class)
class CourseControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CourseService courseService;

    @Test
    @CustomWithMockUser(username = "admin", roles = {"ROLE_ADMIN"}, firstName = "John", lastName = "Doe")
    void getAllCourses() throws Exception {
        List<Course> courseList = Arrays.asList(Source.course);
        given(courseService.findAll()).willReturn(courseList);

        mvc.perform(get("/courses/all"))
                .andExpect(model().attribute("courses", courseList));
    }
}