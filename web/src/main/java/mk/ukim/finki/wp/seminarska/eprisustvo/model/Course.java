package mk.ukim.finki.wp.seminarska.eprisustvo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;            // VP
    private String year;            // 2022/23
    private String semester;        // summer or winter semester
    @ManyToOne
    private Subject subject;
    @ManyToMany
    private List<Professor> professors;

    public Course() {
    }

    public Course(String name, String year, String semester) {
        this.name = name;
        this.year = year;
        this.semester = semester;
    }
}
