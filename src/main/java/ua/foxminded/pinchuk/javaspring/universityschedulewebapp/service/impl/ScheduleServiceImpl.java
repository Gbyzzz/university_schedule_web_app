package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.impl;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.Schedule;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.AppUser;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.repository.ScheduleRepository;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.ScheduleService;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.UserService;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.exception.UniversityServiceException;

import java.time.ZoneId;
import java.util.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ZoneId defaultZoneId = ZoneId.systemDefault();

    private final UserService userService;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, UserService userService) {
        this.scheduleRepository = scheduleRepository;
        this.userService = userService;
    }

    @Override
    public Schedule findScheduleById(int id) throws UniversityServiceException {
        return scheduleRepository.findById(id)
                .orElseThrow(()->new UniversityServiceException("Schedule with id:" +
                        id + " haven't been found in the database"));
    }

    @Override
    public void saveOrUpdate(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    @Override
    public void remove(Schedule schedule) {
        scheduleRepository.delete(schedule);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_TEACHER') or #userId == principal.getUserId())")
    @Override
    public List<Schedule> getScheduleByUser(int userId, LocalDate date, String type) {

            AppUser appUser;
            try {
                appUser = userService.findUserById(userId);
            } catch (UniversityServiceException e) {
                throw new RuntimeException(e);
            }

            Date endDate;
            Date startDate;
            if (type.equals("month")) {
                date = date.withDayOfMonth(1);
                LocalDate end = date.plusMonths(1);
                endDate = Date.from(end.atStartOfDay(defaultZoneId).toInstant());
                startDate = Date.from(date.atStartOfDay(defaultZoneId).toInstant());
            } else {
                LocalDate end = date.plusDays(1);
                endDate = Date.from(end.atStartOfDay(defaultZoneId).toInstant());
                startDate = Date.from(date.atStartOfDay(defaultZoneId).toInstant());
            }

            return scheduleRepository.getSchedulesByCourse_StudentsIsContainingAndStartTimeBetweenOrCourse_TeacherAndStartTimeBetween(appUser, startDate, endDate, appUser, startDate, endDate);
    }

    @Override
    public List<Schedule> getAll() {
        return scheduleRepository.findAll();
    }
}
