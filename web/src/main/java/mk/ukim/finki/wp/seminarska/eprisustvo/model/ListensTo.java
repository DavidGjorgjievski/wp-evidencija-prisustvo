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

    public ListensTo(Student student, Course course, String professor_username) {
        this.student = student;
        this.course = course;
        this.prof_username = professor_username;
        this.id = new ListensToKey(student.getIndex(), course.getId());
    }

    public ListensTo() {

    }

    public Student getStudent() {
        return this.student;
    }

    public Course getCourse() {
        return this.course;
    }
}
