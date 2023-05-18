package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.Source;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.AppUser;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.Schedule;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.ScheduleService;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.UserService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(ScheduleController.class)
class ScheduleControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ScheduleService scheduleService;

    @MockBean
    private UserService userService;

    @ParameterizedTest
    @WithMockUser(username="admin", authorities={"ROLE_TEACHER","ROLE_ADMIN"})
    @MethodSource("ua.foxminded.pinchuk.javaspring.universityschedulewebapp.Source#provideSchedules")
    void getAllMonthScheduleByUserId(List<Schedule> schedules, AppUser appUser, LocalDate date, String type) throws Exception {
        given(scheduleService.getScheduleByUser(appUser.getUserId(), date, type)).willReturn(schedules);
        given(userService.findUserById(appUser.getUserId())).willReturn(appUser);

        mvc.perform(get("/schedules/get")
                        .param("userId", String.valueOf(appUser.getUserId()))
                        .param("date", date.toString())
                        .param("type", type))
                .andExpect(model().attribute("schedules", schedules));
    }
}