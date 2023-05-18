package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.AppUser;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.Course;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.Schedule;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.CourseService;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.ScheduleService;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.UserService;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.exception.UniversityServiceException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final CourseService courseService;
    private final ScheduleService scheduleService;

    public AdminController(UserService userService, CourseService courseService, ScheduleService scheduleService) {
        this.userService = userService;
        this.courseService = courseService;
        this.scheduleService = scheduleService;
    }

    @RequestMapping("")
    String adminPage(Model model){
        return "admin";
    }

    @RequestMapping("/users")
    String adminUsersPage(Model model){
        model.addAttribute("users", userService.findAll());
        return "admin-users";
    }

    @RequestMapping("/courses")
    String adminCoursesPage(Model model){
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("allTeachers", userService.findAllByRole(AppUser.Role.ROLE_TEACHER));
        model.addAttribute("allStudents", userService.findAllByRole(AppUser.Role.ROLE_STUDENT));
        return "admin-courses";
    }

    @RequestMapping("/schedules")
    String adminSchedulesPage(Model model){
        model.addAttribute("courses", courseService.findAll());
        return "admin-schedules";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/users/save")
    RedirectView adminUserSave(@RequestParam int userId,
                               @RequestParam String firstName,
                               @RequestParam String lastName,
                               @RequestParam String email,
                               @RequestParam String role,
                               @RequestParam String phone) throws UniversityServiceException {
        AppUser user = userService.findUserById(userId);
        user.setEmail(email);
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setPhone(phone);
        user.setUserRole(AppUser.Role.valueOf(role));
        userService.saveOrUpdate(user);
        return new RedirectView("/admin/users");
    }

    @PostMapping("/courses/save")
    RedirectView adminCoursesPageSave(@RequestParam(required = false) Integer courseId,
                                      @RequestParam String courseName,
                                      @RequestParam String description,
                                      @RequestParam int[] studentId,
                                      @RequestParam int teacherId) throws UniversityServiceException {
        Course course = new Course();
        if(courseId != null){
            course.setCourseId(courseId);
        }
        List<AppUser> students = new ArrayList<>();
        for(int st : studentId){
            students.add(userService.findUserById(st));
        }
        course.setStudents(students);
        course.setCourseName(courseName);
        course.setTeacher(userService.findUserById(teacherId));
        course.setDescription(description);
        courseService.saveOrUpdate(course);
        return new RedirectView("/admin/courses");
    }


    @RequestMapping("/courses/delete")
    RedirectView adminCoursesPageDelete(@RequestParam int deleteCourseId) throws UniversityServiceException {
        courseService.removeCourse(courseService.findCourseById(deleteCourseId));
        return new RedirectView("/admin/courses");
    }

    @PostMapping("/schedules/get")
    String adminSchedulesPageGet(@RequestParam int userId,
                                 @RequestParam LocalDate date,
                                 @RequestParam String type,
                                 Model model) throws Exception {
        model.addAttribute("schedules_admin", scheduleService.getScheduleByUser(userId, date, type));
        model.addAttribute("courses", courseService.findAll());
        return "admin-schedules";
    }

    @PostMapping("/schedules/save")
    String adminSchedulesPageSave(@RequestParam(required = false) Integer scheduleId,
                                  @RequestParam int courseId,
                                  @RequestParam String startTime,
                                  @RequestParam String endTime) throws Exception {
        Schedule schedule = new Schedule();
        if(scheduleId != null) {
            schedule.setScheduleId(scheduleId);
        }
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        schedule.setCourse(courseService.findCourseById(courseId));

        schedule.setStartTime(formatter.parse(startTime));
        schedule.setEndTime(formatter.parse(endTime));
        scheduleService.saveOrUpdate(schedule);
        return "admin-schedules";
    }
    @PostMapping("/schedules/delete")
    RedirectView adminSchedulesPageDelete(@RequestParam int deleteScheduleId) throws Exception {
        scheduleService.remove(scheduleService.findScheduleById(deleteScheduleId));
        return new RedirectView("/admin/schedules");
    }
}
