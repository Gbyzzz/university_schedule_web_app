package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service;

import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.Course;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.exception.UniversityServiceException;

import java.util.Optional;

public interface CourseService {

    Course findCourseById(int id) throws UniversityServiceException;
    void saveOrUpdate(Course course);

    void removeCourse(Course course);

}
