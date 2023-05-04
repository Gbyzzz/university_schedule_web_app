package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.impl;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.stereotype.Service;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.Schedule;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.User;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.repository.ScheduleRepository;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.ScheduleService;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.exception.UniversityServiceException;

import java.time.ZoneId;
import java.util.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    private ScheduleRepository scheduleRepository;
    private ZoneId defaultZoneId = ZoneId.systemDefault();

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public Schedule findScheduleById(int id) throws UniversityServiceException {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(id);
        if (optionalSchedule.isPresent()) {
            return optionalSchedule.get();
        } else {
            throw new UniversityServiceException("Schedule with id:" + id + " haven't been found in the database");
        }
    }

    @Override
    public void saveOrUpdate(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    @Override
    public void remove(Schedule schedule) {
        scheduleRepository.delete(schedule);
    }

    @Override
    public List<Schedule> getDayScheduleByUser(User user, LocalDate date) {
        LocalDate end = date.plusDays(1);
        Date endDate = Date.from(end.atStartOfDay(defaultZoneId).toInstant());
        Date startDate = Date.from(date.atStartOfDay(defaultZoneId).toInstant());
        return scheduleRepository.getSchedulesByCourse_StudentsIsContainingAndStartTimeBetweenOrCourse_TeacherAndStartTimeBetween(user, startDate, endDate, user, startDate, endDate);
    }

    @Override
    public List<Schedule> getMonthScheduleByUser(User user, LocalDate date) {
        date = date.withDayOfMonth(1);
        LocalDate end = date.plusMonths(1);
        Date endDate = Date.from(end.atStartOfDay(defaultZoneId).toInstant());
        Date startDate = Date.from(date.atStartOfDay(defaultZoneId).toInstant());
        return scheduleRepository.getSchedulesByCourse_StudentsIsContainingAndStartTimeBetweenOrCourse_TeacherAndStartTimeBetween(user, startDate, endDate, user, startDate, endDate);
    }

    @Override
    public List<Schedule> getAll() {
        return scheduleRepository.findAll();
    }
}
