package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.controller;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.IntegrationTestBase;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.Source;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.AppUser;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.Course;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.Schedule;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.repository.CourseRepository;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.repository.ScheduleRepository;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.repository.UserRepository;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.CourseService;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.ScheduleService;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.UserService;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.utils.CustomWithMockUser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(AdminController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdminControllerTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mvc;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ScheduleService scheduleService;

    @Test
    @Order(1)
    @CustomWithMockUser(username = "admin", roles = {"ROLE_ADMIN"}, firstName = "John", lastName = "Doe")
    void adminUsersPage() throws Exception {

        mvc.perform(get("/admin/users"))
                .andExpect(model().attribute("users", Source.users));
    }

    @Test
    @Order(2)
    @CustomWithMockUser(username = "admin", roles = {"ROLE_ADMIN"}, firstName = "John", lastName = "Doe")
    void adminCoursesPage() throws Exception {

        mvc.perform(get("/admin/courses"))
                .andExpect(model().attribute("courses", Source.courses))
                .andExpect(model().attribute("allTeachers", Source.teachers))
                .andExpect(model().attribute("allStudents", Source.students));
    }

    @Test
    @Order(3)
    @CustomWithMockUser(username = "admin", roles = {"ROLE_ADMIN"}, firstName = "John", lastName = "Doe")
    void adminSchedulesPage() throws Exception {

        mvc.perform(get("/admin/schedules"))
                .andExpect(model().attribute("courses", Source.courses));
    }

    @Test
    @Order(4)
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    void adminUserSave() throws Exception {
       Source.users.get(6).setFirstName("Adam");;

        mvc.perform(put("/admin/users/save")
                        .param("userId", "7")
                        .param("firstName", "Adam")
                        .param("lastName", "Taylor")
                        .param("email", "student4@university.com")
                        .param("role", "ROLE_STUDENT")
                        .param("phone", "7654348946")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
        assertThat(Source.users).hasSameElementsAs(userRepository.findAll());
        assertThat(Source.users).hasSameElementsAs(userService.findAll());
    }

    @Test
    @Order(5)
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    void adminUserAdd() throws Exception {
        AppUser newUser = new AppUser(8, "student5@university.com",
                "$2a$10$kTtZAqCVvwUk0V3q3igTj.isbnf9xhGNIY/Tnn6TPJZYmlSc4lo.m",
                "Johnny", "Thompson", AppUser.Role.ROLE_STUDENT, "1654321");
        Source.users.add(newUser);
        Source.students.add(newUser);

        mvc.perform(post("/admin/users/add")
                        .param("firstName", "Johnny")
                        .param("lastName", "Thompson")
                        .param("email", "student5@university.com")
                        .param("role", "ROLE_STUDENT")
                        .param("password", "132435")
                        .param("phone", "1654321")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
        assertThat(Source.users).hasSameElementsAs(userRepository.findAll());
        assertThat(Source.users).hasSameElementsAs(userService.findAll());
    }

    @Test
    @Order(6)
    @WithMockUser(username = "admin", authorities = {"ROLE_TEACHER", "ROLE_ADMIN"})
    void adminCoursesPageSave() throws Exception {
        Source.courses.get(Source.courses.indexOf(new Course(2, "Biology",
                "Biology lessons", Source.users.get(2),
                Arrays.asList(Source.users.get(4), Source.users.get(5), Source.users.get(6)))))
                .setCourseName("Bio");


        mvc.perform(put("/admin/courses/save")
                        .param("courseId", "2")
                        .param("courseName", "Bio")
                        .param("description", "Biology lessons")
                        .param("studentId", "5", "6", "7")
                        .param("teacherId", "3")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
        List<Course> courses = courseRepository.findAll();
        List<Course> courses1 = courseService.findAll();
        assertThat(Source.courses).hasSameElementsAs(courseRepository.findAll());
        assertThat(Source.courses).hasSameElementsAs(courseService.findAll());
    }

    @Test
    @Order(7)
    @WithMockUser(username = "admin", authorities = {"ROLE_TEACHER", "ROLE_ADMIN"})
    void adminCoursesPageAdd() throws Exception {
        Source.courses.add(
                new Course(3, "Course1", "Course1",
                        Source.users.get(1), new ArrayList<>() {{
                    add(Source.users.get(4));
                    add(Source.users.get(6));
                }}));
        mvc.perform(post("/admin/courses/add")
                        .param("courseName", "Course1")
                        .param("description", "Course1")
                        .param("studentId", "5", "7")
                        .param("teacherId", "2")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
        assertThat(Source.courses).hasSameElementsAs(courseRepository.findAll());
        assertThat(Source.courses).hasSameElementsAs(courseService.findAll());
    }

    @Test
    @Order(8)
    @WithMockUser(username = "admin", authorities = {"ROLE_TEACHER", "ROLE_ADMIN"})
    void adminCoursesPageDelete() throws Exception {
        Source.courses.remove(0);
        Source.schedules.remove(4);
        Source.schedules.remove(2);
        Source.schedules.remove(0);


        mvc.perform(delete("/admin/courses/delete")
                        .param("deleteCourseId", "1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
        assertThat(Source.courses).hasSameElementsAs(courseRepository.findAll());
        assertThat(Source.courses).hasSameElementsAs(courseService.findAll());
    }


    @ParameterizedTest
    @Order(12)
    @CustomWithMockUser(username = "admin", roles = {"ROLE_ADMIN"}, firstName = "John", lastName = "Doe")
    @MethodSource("ua.foxminded.pinchuk.javaspring.universityschedulewebapp.Source#provideSchedules")
    void adminSchedulesPageGet(List<Schedule> schedules, AppUser appUser, LocalDate date, String type) throws Exception {
        List<Schedule> schedules1 = Source.schedules;
        List<Schedule> schedules2 = scheduleRepository.findAll();
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
    @Order(9)
    @CustomWithMockUser(username = "admin", roles = {"ROLE_ADMIN"}, firstName = "John", lastName = "Doe")
    void adminSchedulesPageSave() throws Exception {
        Source.schedules.get(
                Source.schedules.indexOf(new Schedule(2, Source.courses.get(0),
                        Source.sdf.parse("2023-03-23 10:00"),
                        Source.sdf.parse("2023-03-23 10:45"))))
                .setEndTime(Source.sdf.parse("2023-03-23 11:00"));
        mvc.perform(put("/admin/schedules/save")
                        .param("scheduleId", "2")
                        .param("courseId", "2")
                        .param("startTime", "2023-03-23T10:00")
                        .param("endTime", "2023-03-23T11:00")
                        .with(csrf()))
                .andExpect(status().isOk());
        List<Schedule> schedules = scheduleRepository.findAll();
        assertThat(Source.schedules).hasSameElementsAs(scheduleRepository.findAll());
    }

    @Test
    @Order(10)
    @CustomWithMockUser(username = "admin", roles = {"ROLE_ADMIN"}, firstName = "John", lastName = "Doe")
    void adminSchedulesPageAdd() throws Exception {
        Source.schedules.add(new Schedule(8,  Source.courses.get(0),
                Source.sdf.parse("2023-04-26 09:00"),
                Source.sdf.parse("2023-04-26 09:45")));

        mvc.perform(post("/admin/schedules/add")
                        .param("courseId", "2")
                        .param("startTime", "2023-04-26T09:00")
                        .param("endTime", "2023-04-26T09:45")
                        .with(csrf()))
                .andExpect(status().isOk());
        List<Schedule> schedules = scheduleRepository.findAll();
        assertThat(Source.schedules).hasSameElementsAs(scheduleRepository.findAll());
    }

    @Test
    @Order(11)
    @WithMockUser(username = "admin", authorities = {"ROLE_TEACHER", "ROLE_ADMIN"})
    void adminSchedulesPageDelete() throws Exception {
        Source.schedules.remove(Source.schedules.indexOf(
                new Schedule(6, Source.courses.get(0),
                        Source.sdf.parse("2023-03-26 10:00"),
                        Source.sdf.parse("2023-03-26 10:45"))));

        mvc.perform(delete("/admin/schedules/delete")
                        .param("deleteScheduleId", "6")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
        List<Schedule> schedules = scheduleRepository.findAll();
        assertThat(Source.schedules).hasSameElementsAs(scheduleRepository.findAll());
    }
}