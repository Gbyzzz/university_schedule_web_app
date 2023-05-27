package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.impl;


import org.springframework.stereotype.Service;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.AppUser;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.Course;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.repository.CourseRepository;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.repository.UserRepository;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.CourseService;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.UserService;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.exception.UniversityServiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserService userService;

    public CourseServiceImpl(CourseRepository courseRepository, UserService userService) {
        this.courseRepository = courseRepository;
        this.userService = userService;
    }

    @Override
    public Course findCourseById(int id) throws UniversityServiceException {
        return courseRepository.findById(id)
                .orElseThrow(()-> new UniversityServiceException("Course with id:"
                        + id + " haven't been found in the database"));
    }

    @Override
    public void saveOrUpdate(Integer courseId, String name, String description, int[] studentsId, int teacherId) throws UniversityServiceException {
        Course course = new Course();
        if(courseId != null){
            course.setCourseId(courseId);
        }
        List<AppUser> students = new ArrayList<>();
        for(int st : studentsId){
            students.add(userService.findUserById(st));
        }
        course.setStudents(students);
        course.setCourseName(name);
        course.setTeacher(userService.findUserById(teacherId));
        course.setDescription(description);
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
