package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.impl;


import org.springframework.stereotype.Service;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.Course;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.repository.CourseRepository;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.CourseService;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.exception.UniversityServiceException;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Course findCourseById(int id) throws UniversityServiceException {
        return courseRepository.findById(id)
                .orElseThrow(()-> new UniversityServiceException("Course with id:"
                        + id + " haven't been found in the database"));
    }

    @Override
    public void saveOrUpdate(Course course) {
        courseRepository.save(course);
    }

    @Override
    public void removeCourse(Course course) {
        courseRepository.delete(course);
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }
}
