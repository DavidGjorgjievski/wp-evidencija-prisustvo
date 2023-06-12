package mk.ukim.finki.wp.seminarska.eprisustvo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Student {
    @Id
    private String index;
    private String name;
    private String surname;
    private String email;
    private String password;

    public Student() {
    }

    public Student(String index, String name, String surname, String email, String password) {
        this.index = index;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }
//
//    public void reportAttendanceToActivity(String code) {
//
//    }
}
