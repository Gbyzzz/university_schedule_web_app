package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service;

import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.Schedule;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.User;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.exception.UniversityServiceException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
    Schedule findScheduleById(int id) throws UniversityServiceException;

    void saveOrUpdate(Schedule schedule);

    void remove(Schedule schedule);
    List<Schedule> getDayScheduleByUser(User user, LocalDate date);

    List<Schedule> getMonthScheduleByUser(User user, LocalDate date);
}
