package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.Source;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.AppUser;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.Course;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.Schedule;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.CourseService;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.ScheduleService;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.UserService;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.exception.UniversityServiceException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private CourseService courseService;
    @MockBean
    private ScheduleService scheduleService;

    @Test
    @WithMockUser(username="admin", authorities={"ROLE_ADMIN"})
    void adminUsersPage() throws Exception {
        given(userService.findAll()).willReturn(Source.users);

        mvc.perform(get("/admin/users"))
                .andExpect(model().attribute("users", Source.users));
    }

    @Test
    @WithMockUser(username="admin", authorities={"ROLE_ADMIN", "ROLE_TEACHER"})
    void adminCoursesPage() throws Exception {
        given(courseService.findAll()).willReturn(Source.courses);
        given(userService.findAllByRole(AppUser.Role.ROLE_TEACHER)).willReturn(Source.teachers);
        given(userService.findAllByRole(AppUser.Role.ROLE_STUDENT)).willReturn(Source.students);

        mvc.perform(get("/admin/courses"))
                .andExpect(model().attribute("courses", Source.courses))
                .andExpect(model().attribute("allTeachers", Source.teachers))
                .andExpect(model().attribute("allStudents", Source.students));
    }

    @Test
    @WithMockUser(username="admin", authorities={"ROLE_ADMIN", "ROLE_TEACHER"})
    void adminSchedulesPage() throws Exception {
        given(courseService.findAll()).willReturn(Source.courses);
        mvc.perform(get("/admin/courses"))
                .andExpect(model().attribute("courses", Source.courses));
    }

    @Test
    @WithMockUser(username="admin", authorities={"ROLE_ADMIN"})
    void adminUserSave() throws Exception {
        given(userService.findUserById(2)).willReturn(Source.appUser2);
        AppUser appUser = new AppUser(2, "student5@university.com", "123456",
                "Adam", "Thompson", AppUser.Role.ROLE_STUDENT,"164321");
        doNothing().when(userService).saveOrUpdate(isA(int.class), isA(String.class), isA(String.class), isA(String.class), isA(String.class), isA(String.class), isA(String.class));

        mvc.perform(post("/admin/users/save")
                .param("userId", "2")
                .param("firstName", "Adam")
                .param("lastName", "Thompson")
                .param("email", "student5@university.com")
                .param("role", "ROLE_STUDENT")
                .param("phone", "164321")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username="admin", authorities={"ROLE_TEACHER","ROLE_ADMIN"})
    void adminCoursesPageSave() throws Exception {
        given(userService.findUserById(1)).willReturn(Source.appUser1);
        given(userService.findUserById(2)).willReturn(Source.appUser2);
        given(userService.findUserById(3)).willReturn(Source.appUser3);
        doNothing().when(courseService).saveOrUpdate(isA(Integer.class), isA(String.class), isA(String.class), isA(int[].class),isA(int.class));

        mvc.perform(post("/admin/courses/save")
                        .param("courseId", "2")
                        .param("courseName", "Course1")
                        .param("description", "Course1")
                        .param("studentId", "2", "3")
                        .param("teacherId", "1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username="admin", authorities={"ROLE_TEACHER","ROLE_ADMIN"})
    void adminCoursesPageDelete() throws Exception {
        given(courseService.findCourseById(1)).willReturn(Source.course);
        doNothing().when(courseService).removeCourse(isA(Course.class));

        mvc.perform(post("/admin/courses/delete")
                        .param("deleteCourseId", "1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }


    @ParameterizedTest
    @WithMockUser(username="admin", authorities={"ROLE_TEACHER","ROLE_ADMIN"})
    @MethodSource("ua.foxminded.pinchuk.javaspring.universityschedulewebapp.Source#provideSchedules")
    void adminSchedulesPageGet(List<Schedule> schedules, AppUser appUser, LocalDate date, String type) throws Exception {
        given(scheduleService.getScheduleByUser(appUser.getUserId(), date, type)).willReturn(schedules);
        given(courseService.findAll()).willReturn(Source.courses);

        mvc.perform(post("/admin/schedules/get")
                        .param("userId", String.valueOf(appUser.getUserId()))
                        .param("date", date.toString())
                        .param("type", type)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("schedules_admin", schedules))
                .andExpect(model().attribute("courses", Source.courses));

    }

    @Test
    @WithMockUser(username="admin", authorities={"ROLE_TEACHER","ROLE_ADMIN"})
    void adminSchedulesPageSave() throws Exception {
        given(courseService.findCourseById(1)).willReturn(Source.course);
        doNothing().when(scheduleService).saveOrUpdate(isA(Integer.class), isA(Integer.class), isA(String.class), isA(String.class));

        mvc.perform(post("/admin/schedules/save")
                        .param("scheduleId", "1")
                        .param("courseId", "1")
                        .param("startTime", "2023-03-26T09:00")
                        .param("endTime", "2023-03-26T09:45")
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="admin", authorities={"ROLE_TEACHER","ROLE_ADMIN"})
    void adminSchedulesPageDelete() throws Exception {
        given(scheduleService.findScheduleById(1)).willReturn(Source.schedule1);
        doNothing().when(scheduleService).remove(isA(Schedule.class));

        mvc.perform(post("/admin/schedules/delete")
                .param("deleteScheduleId", "1")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }
}