package mk.ukim.finki.wp.seminarska.eprisustvo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ListensToKey implements Serializable {
    @Column(name = "student_index")
    String studentIndex;

    @Column(name = "course_id")
    Long courseId;
}
