package ua.foxminded.pinchuk.javaspring.universityschedulewebapp;

import org.junit.jupiter.params.provider.Arguments;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.AppUser;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.Course;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.Schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public class Source {
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static List<AppUser> users = new ArrayList<>() {{
        add(new AppUser(1, "admin@university.com",
                "$2a$10$kTtZAqCVvwUk0V3q3igTj.isbnf9xhGNIY/Tnn6TPJZYmlSc4lo.m",
                "John", "Smith", AppUser.Role.ROLE_ADMIN, "138765487"));
        add(new AppUser(2, "teacher1@university.com",
                "$2a$10$kTtZAqCVvwUk0V3q3igTj.isbnf9xhGNIY/Tnn6TPJZYmlSc4lo.m",
                "Tom", "Green", AppUser.Role.ROLE_TEACHER, "135481314"));
        add(new AppUser(3, "teacher2@university.com",
                "$2a$10$kTtZAqCVvwUk0V3q3igTj.isbnf9xhGNIY/Tnn6TPJZYmlSc4lo.m",
                "Anthony", "Newman", AppUser.Role.ROLE_TEACHER, "846321871"));
        add(new AppUser(4, "student1@university.com",
                "$2a$10$kTtZAqCVvwUk0V3q3igTj.isbnf9xhGNIY/Tnn6TPJZYmlSc4lo.m",
                "Kimberly", "Foster", AppUser.Role.ROLE_STUDENT, "9318431812"));
        add(new AppUser(5, "student2@university.com",
                "$2a$10$kTtZAqCVvwUk0V3q3igTj.isbnf9xhGNIY/Tnn6TPJZYmlSc4lo.m",
                "Daniel", "Jones", AppUser.Role.ROLE_STUDENT, "7313544342"));
        add(new AppUser(6, "student3@university.com",
                "$2a$10$kTtZAqCVvwUk0V3q3igTj.isbnf9xhGNIY/Tnn6TPJZYmlSc4lo.m",
                "William", "Brown", AppUser.Role.ROLE_STUDENT, "3246841321"));
        add(new AppUser(7, "student4@university.com",
                "$2a$10$kTtZAqCVvwUk0V3q3igTj.isbnf9xhGNIY/Tnn6TPJZYmlSc4lo.m",
                "Edward", "Taylor", AppUser.Role.ROLE_STUDENT, "7654348946"));
    }};
    public static List<Course> courses = new ArrayList<>() {{
        add(new Course(1, "Math",
                "Math lessons", users.get(1),
                Arrays.asList(users.get(3), users.get(4), users.get(5))));
        add(new Course(2, "Biology",
                "Biology lessons", users.get(2),
                Arrays.asList(users.get(4), users.get(5), users.get(6))));
    }};

    public static List<Schedule> schedules = new ArrayList<>() {{
        try {
            add(new Schedule(1, courses.get(0),
                    sdf.parse("2023-03-23 09:00"),
                    sdf.parse("2023-03-23 09:45")));
            add(new Schedule(2, courses.get(1),
                    sdf.parse("2023-03-23 10:00"),
                    sdf.parse("2023-03-23 10:45")));
            add(new Schedule(3, courses.get(0),
                    sdf.parse("2023-03-24 09:00"),
                    sdf.parse("2023-03-24 09:45")));
            add(new Schedule(4, courses.get(1),
                    sdf.parse("2023-03-24 10:00"),
                    sdf.parse("2023-03-24 10:45")));
            add(new Schedule(5, courses.get(0),
                    sdf.parse("2023-03-26 09:00"),
                    sdf.parse("2023-03-26 09:45")));
            add(new Schedule(6, courses.get(1),
                    sdf.parse("2023-03-26 10:00"),
                    sdf.parse("2023-03-26 10:45")));
            add(new Schedule(7, courses.get(1),
                    sdf.parse("2023-04-01 10:00"),
                    sdf.parse("2023-04-01 10:45")));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }};


    public static List<AppUser> teachers = new ArrayList<>() {{
        add(users.get(1));
        add(users.get(2));
    }};
    public static List<AppUser> students = new ArrayList<>() {{
        add(users.get(3));
        add(users.get(4));
        add(users.get(5));
        add(users.get(6));
    }};


    public static Stream<Arguments> provideSchedules() throws ParseException {
        return Stream.of(
                Arguments.of(new ArrayList<Schedule>() {{
                                 add(schedules.get(0));
                             }}
                        , users.get(4),
                        LocalDate.of(2023, 3, 23), "day"),
                Arguments.of(new ArrayList<Schedule>() {{
                                 add(schedules.get(0));
                                 add(schedules.get(1));
                             }}
                        , users.get(5),
                        LocalDate.of(2023, 03, 26), "month"),
                Arguments.of(new ArrayList<Schedule>() {{
                                 add(schedules.get(3));
                                 add(schedules.get(2));
                             }}
                        , users.get(6),
                        LocalDate.of(2023, 04, 01), "month"));
    }
}
