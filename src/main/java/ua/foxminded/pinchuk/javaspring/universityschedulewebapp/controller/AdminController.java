package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.AppUser;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.CourseService;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.ScheduleService;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.UserService;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.exception.UniversityServiceException;

import java.time.LocalDate;

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
    String adminPage() {
        return "admin";
    }

    @RequestMapping("/users")
    String adminUsersPage(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin-users";
    }

    @RequestMapping("/courses")
    String adminCoursesPage(Model model) {
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("allTeachers", userService.findAllByRole(AppUser.Role.ROLE_TEACHER));
        model.addAttribute("allStudents", userService.findAllByRole(AppUser.Role.ROLE_STUDENT));
        return "admin-courses";
    }

    @RequestMapping("/schedules")
    String adminSchedulesPage(Model model) {
        model.addAttribute("courses", courseService.findAll());
        return "admin-schedules";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/users/save")
    RedirectView adminUserSave(@RequestParam int userId,
                               @RequestParam String firstName,
                               @RequestParam String lastName,
                               @RequestParam String email,
                               @RequestParam String role,
                               @RequestParam String phone,
                               RedirectAttributes redirectAttributes) throws UniversityServiceException {
            userService.saveOrUpdate(userId, null, firstName, lastName, email, role, phone);
        return new RedirectView("/admin/users");
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/users/add")
    RedirectView adminUserAdd(@RequestParam String firstName,
                              @RequestParam String lastName,
                              @RequestParam String email,
                              @RequestParam String role,
                              @RequestParam String password,
                              @RequestParam String phone,
                              RedirectAttributes redirectAttributes) throws UniversityServiceException {
            userService.saveOrUpdate(null, password, firstName, lastName, email, role, phone);
        return new RedirectView("/admin/users");
    }

    @PutMapping("/courses/save")
    RedirectView adminCoursesPageSave(@RequestParam Integer courseId,
                                      @RequestParam String courseName,
                                      @RequestParam String description,
                                      @RequestParam int[] studentId,
                                      @RequestParam int teacherId,
                                      RedirectAttributes redirectAttributes) throws UniversityServiceException {
            courseService.saveOrUpdate(courseId, courseName, description, studentId, teacherId);

        return new RedirectView("/admin/courses");
    }

    @PostMapping("/courses/add")
    RedirectView adminCoursesPageAdd(@RequestParam String courseName,
                                     @RequestParam String description,
                                     @RequestParam int[] studentId,
                                     @RequestParam int teacherId,
                                     RedirectAttributes redirectAttributes) throws UniversityServiceException {
            courseService.saveOrUpdate(null, courseName, description, studentId, teacherId);
        return new RedirectView("/admin/courses");
    }


    @DeleteMapping("/courses/delete")
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

    @PutMapping("/schedules/save")
    String adminSchedulesPageSave(@RequestParam Integer scheduleId,
                                  @RequestParam int courseId,
                                  @RequestParam String startTime,
                                  @RequestParam String endTime) throws Exception {
        scheduleService.saveOrUpdate(scheduleId, courseId, startTime, endTime);
        return "admin-schedules";
    }

    @PostMapping("/schedules/add")
    String adminSchedulesPageAdd(@RequestParam int courseId,
                                 @RequestParam String startTime,
                                 @RequestParam String endTime) throws Exception {
        scheduleService.saveOrUpdate(null, courseId, startTime, endTime);
        return "admin-schedules";
    }

    @DeleteMapping("/schedules/delete")
    RedirectView adminSchedulesPageDelete(@RequestParam int deleteScheduleId) throws Exception {
        scheduleService.remove(scheduleService.findScheduleById(deleteScheduleId));
        return new RedirectView("/admin/schedules");
    }
}
