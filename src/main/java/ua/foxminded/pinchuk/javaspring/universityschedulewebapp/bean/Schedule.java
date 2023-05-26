package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;


@Table(name = "schedules")
@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer scheduleId;

    @OneToOne()
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    public Schedule() {
    }

    public Schedule(Integer scheduleId, Course course, Date startTime, Date endTime) {
        this.scheduleId = scheduleId;
        this.course = course;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return scheduleId == schedule.scheduleId &&
                Objects.equals(course, schedule.course) &&
                Objects.equals(startTime, schedule.startTime) &&
                Objects.equals(endTime, schedule.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleId, course, startTime, endTime);
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "scheduleId=" + scheduleId +
                ", course=" + course +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                "}/n" ;
    }
}
