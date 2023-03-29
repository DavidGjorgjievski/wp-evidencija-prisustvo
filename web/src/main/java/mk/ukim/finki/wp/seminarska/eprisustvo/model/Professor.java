package mk.ukim.finki.wp.seminarska.eprisustvo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Professor {
    @Id
    private String username;
    private String name;
    private String surname;
    private String email;
    private String password;

    public Professor() {
    }

    public Professor(String username, String name, String surname, String email, String password) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }
}
