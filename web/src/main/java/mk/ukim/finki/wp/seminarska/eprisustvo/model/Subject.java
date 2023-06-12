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
    private String code;        // CSES401
    private String name;        // Operating Systems
    private Integer num_credits;    // 6
    private Integer semester;       // 1, 2, 3, 4, 5, 6.....

    public Subject() {
    }

    public Subject(String code, String name, Integer num_credits, Integer semester) {
        this.code = code;
        this.name = name;
        this.num_credits = num_credits;
        this.semester = semester;
    }
}
