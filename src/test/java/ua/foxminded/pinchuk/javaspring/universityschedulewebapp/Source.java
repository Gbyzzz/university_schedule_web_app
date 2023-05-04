package ua.foxminded.pinchuk.javaspring.universityschedulewebapp;

import org.junit.jupiter.params.provider.Arguments;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.Course;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.Schedule;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Stream;

public class Source {

    public static User user1 = new User(1, "abc@gmail.com", "123456", "Adam", "Green", User.Role.ROLE_TEACHER, "12345964634");
    public static User user2 = new User(2, "def@gmail.com", "123456", "John", "Cole", User.Role.ROLE_STUDENT, "13132423452");
    public static User user3 = new User(3, "ghi@gmail.com", "123456", "Tommy", "Brown", User.Role.ROLE_STUDENT, "1234124235");
    public static Course course = new Course(1, "Course", "New Course", user1, Arrays.asList(user2, user3));
    public static Schedule schedule1 = new Schedule(1, course, new Date(2023, 03, 26, 9, 0), new Date(2023, 03, 26, 9, 45));
    public static Schedule schedule2 = new Schedule(1, course, new Date(2023, 03, 28, 9, 0), new Date(2023, 03, 28, 9, 45));

    public static Schedule schedule3 = new Schedule(1, course, new Date(2023, 04, 26, 9, 0), new Date(2023, 04, 26, 9, 45));

    public static Stream<Arguments> provideSchedules() {
        return Stream.of(
                Arguments.of(new ArrayList<Schedule>() {{
                                 add(schedule1);
                             }}
                        , user3, LocalDate.of(2023, 3, 26), "day"),
                Arguments.of(new ArrayList<Schedule>() {{
                                 add(schedule1);
                                 add(schedule2);
                             }}
                        , user3, LocalDate.of(2023, 03, 26), "month"),
                Arguments.of(new ArrayList<Schedule>() {{
                                 add(schedule3);
                             }}
                        , user3, LocalDate.of(2023, 04, 01), "month"));
    }
}
