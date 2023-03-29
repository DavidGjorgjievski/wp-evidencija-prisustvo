package mk.ukim.finki.wp.seminarska.eprisustvo.model;

import jakarta.persistence.*;
import lombok.Data;
import mk.ukim.finki.wp.seminarska.eprisustvo.model.enumerations.ActivityStatus;

import java.time.LocalDateTime;
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
    @ManyToMany
    private List<Professor> professors;
    @ManyToMany
    private List<Student> students;

    public Activity() {
    }

    public Activity(String title, String code, LocalDateTime open_date, LocalDateTime close_date, String location, Integer num_attendees, ActivityStatus activityStatus) {
        this.title = title;
        this.code = code;
        this.open_date = open_date;
        this.close_date = close_date;
        this.location = location;
        this.num_attendees = 0;
        this.activityStatus = activityStatus;
    }
}
