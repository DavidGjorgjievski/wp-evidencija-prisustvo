package mk.ukim.finki.wp.seminarska.eprisustvo.model;

import jakarta.persistence.*;
import lombok.Data;
import mk.ukim.finki.wp.seminarska.eprisustvo.model.enumerations.ActivityStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String code;
    private LocalDateTime open_date;
    private LocalDateTime close_date;
    private String location;
    private Integer num_attendees;
    @Enumerated(EnumType.STRING)
    private ActivityStatus activityStatus;
    @ManyToOne
    private Course course;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Professor> professors;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Student> students;

    public Activity() {
    }

    public Activity(String title,
                    String code,
                    LocalDateTime open_date,
                    LocalDateTime close_date,
                    String location,
                    Course course,
                    List<Professor> professors) {
        this.title = title;
        this.code = code;
        this.open_date = open_date;
        this.close_date = close_date;
        this.location = location;
        this.num_attendees = 0;
        this.activityStatus = ActivityStatus.CREATED;
        this.course = course;
        this.professors = professors;
        this.students = new ArrayList<>();
    }

    public void addAttendee(Student student) {
        this.students.add(student);
        this.num_attendees += 1;
    }

    public void removeAttendee(Student student) {
        this.students.remove(student);
        this.num_attendees -= 1;
    }

    public void finishActivity() {
        this.activityStatus = ActivityStatus.FINISHED;
    }

    public void cancelActivity() {
        this.activityStatus = ActivityStatus.CANCELED;
    }
}
