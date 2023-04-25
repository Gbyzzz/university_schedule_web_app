package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.controller;

import org.springframework.stereotype.Controller;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.Course;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.CourseService;

import java.util.List;

@Controller
public class CourseController {

    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

//    public List<Course> getDaySchedule() {
//        return courseService.
//    }
}
