package mk.ukim.finki.wp.seminarska.eprisustvo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;            // Veb Programiranje

    private Integer num_credits;    // 6

    private String semester;        // first, second, trird semester etc.

    public Subject() {
    }

    public Subject(String name, Integer num_credits, String semester) {
        this.name = name;
        this.num_credits = num_credits;
        this.semester = semester;
    }
}
