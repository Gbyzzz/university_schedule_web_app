package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service;

import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.Course;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.exception.UniversityServiceException;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    Course findCourseById(int id) throws UniversityServiceException;
    void saveOrUpdate(Integer courseId, String name, String description, int[] studentsId, int teacherId) throws UniversityServiceException;

    void removeCourse(Course course);
    List<Course> findAll();

}
