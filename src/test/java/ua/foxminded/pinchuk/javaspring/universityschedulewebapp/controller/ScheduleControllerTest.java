package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.Source;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.Schedule;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.User;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.ScheduleService;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.UserService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ScheduleController.class)
class ScheduleControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ScheduleService scheduleService;

    @MockBean
    private UserService userService;

    @Test
    void cocktailsPage() throws Exception {
        List<Schedule> scheduleList = Arrays.asList(Source.schedule1, Source.schedule2);
        given(scheduleService.getAll()).willReturn(scheduleList);

        mvc.perform(get("/schedule"))
                .andExpect(model().attribute("all_schedules", scheduleList));
    }

    @ParameterizedTest
    @MethodSource("ua.foxminded.pinchuk.javaspring.universityschedulewebapp.Source#provideSchedules")
    void getAllMonthScheduleByUserId(List<Schedule> schedules, User user, LocalDate date, String type) throws Exception {
        given(scheduleService.getDayScheduleByUser(user, date)).willReturn(schedules);
        given(userService.findUserById(user.getUserId())).willReturn(user);

        mvc.perform(get("/schedule/get")
                        .param("userId", String.valueOf(user.getUserId()))
                        .param("date", date.toString())
                        .param("type", type))
                .andExpect(model().attributeExists("schedules"));
    }
}