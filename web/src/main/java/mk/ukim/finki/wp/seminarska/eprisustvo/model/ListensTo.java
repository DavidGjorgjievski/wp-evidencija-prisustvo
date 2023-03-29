package mk.ukim.finki.wp.seminarska.eprisustvo.model;

import jakarta.persistence.*;

@Entity
public class ListensTo {
    @EmbeddedId
    ListensToKey id;

    @ManyToOne
    @MapsId("studentIndex")
    @JoinColumn(name = "student_index")
    Student student;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    Course course;

    String prof_username;
}
