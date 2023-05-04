package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.Schedule;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.User;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    //    List<Schedule> getSchedulesByCourse_StudentsIsContainingOrCourse_TeacherIsContainingAndCourseIsBetween(User user, Date date);
    List<Schedule> getSchedulesByCourse_StudentsIsContainingAndStartTimeBetweenOrCourse_TeacherAndStartTimeBetween(User user, Date start, Date end, User user1, Date start1, Date end1);
}
