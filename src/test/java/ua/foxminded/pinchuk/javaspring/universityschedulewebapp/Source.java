package ua.foxminded.pinchuk.javaspring.universityschedulewebapp;

import org.junit.jupiter.params.provider.Arguments;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.AppUser;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.Course;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.Schedule;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public class Source {

    public static AppUser appUser1 = new AppUser(1, "abc@gmail.com",
            "123456", "Adam", "Green",
            AppUser.Role.ROLE_TEACHER, "12345964634");
    public static AppUser appUser2 = new AppUser(2, "def@gmail.com",
            "123456", "John", "Cole",
            AppUser.Role.ROLE_STUDENT, "13132423452");
    public static AppUser appUser3 = new AppUser(3, "ghi@gmail.com",
            "123456", "Tommy", "Brown",
            AppUser.Role.ROLE_STUDENT, "1234124235");
    public static Course course = new Course(1, "Course",
            "New Course", appUser1, Arrays.asList(appUser2, appUser3));
    public static Schedule schedule1 = new Schedule(1, course,
            new Date(2023, 03, 26, 9, 0), new Date(2023, 03, 26, 9, 45));
    public static Schedule schedule2 = new Schedule(1, course, new Date(2023, 03, 28, 9, 0), new Date(2023, 03, 28, 9, 45));

    public static Schedule schedule3 = new Schedule(1, course, new Date(2023, 04, 26, 9, 0), new Date(2023, 04, 26, 9, 45));

    public static List<AppUser> users = new ArrayList<>() {{
        add(appUser1);
        add(appUser2);
        add(appUser3);
    }};
    public static List<AppUser> teachers = new ArrayList<>() {{
        add(appUser1);
    }};
    public static List<AppUser> students = new ArrayList<>() {{
        add(appUser2);
        add(appUser3);
    }};
    public static List<Course> courses = new ArrayList<>() {{
        add(course);
    }};

    public static Stream<Arguments> provideSchedules() {
        return Stream.of(
                Arguments.of(new ArrayList<Schedule>() {{
                                 add(schedule1);
                             }}
                        , appUser3, LocalDate.of(2023, 3, 26), "day"),
                Arguments.of(new ArrayList<Schedule>() {{
                                 add(schedule1);
                                 add(schedule2);
                             }}
                        , appUser3, LocalDate.of(2023, 03, 26), "month"),
                Arguments.of(new ArrayList<Schedule>() {{
                                 add(schedule3);
                             }}
                        , appUser3, LocalDate.of(2023, 04, 01), "month"));
    }
}
