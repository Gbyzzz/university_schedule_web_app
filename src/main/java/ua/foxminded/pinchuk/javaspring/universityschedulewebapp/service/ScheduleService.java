package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service;

import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.Schedule;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.AppUser;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.exception.UniversityServiceException;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
    Schedule findScheduleById(int id) throws UniversityServiceException;
    void remove(Schedule schedule);
    List<Schedule> getScheduleByUser(int userId, LocalDate date, String type) throws Exception;
    List<Schedule> getAll();

    void saveOrUpdate(Integer scheduleId, int courseId, String startTime, String endTime) throws UniversityServiceException, ParseException;
}
