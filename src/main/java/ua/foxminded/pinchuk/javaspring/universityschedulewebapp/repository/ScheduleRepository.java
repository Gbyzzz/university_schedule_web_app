package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.AppUser;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.Schedule;

import java.util.Date;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    List<Schedule> getSchedulesByCourse_StudentsIsContainingAndStartTimeBetweenOrCourse_TeacherAndStartTimeBetween(AppUser appUser, Date start, Date end, AppUser appUser1, Date start1, Date end1);
}
