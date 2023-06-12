package mk.ukim.finki.wp.seminarska.eprisustvo.service;

import mk.ukim.finki.wp.seminarska.eprisustvo.model.Student;
import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<Student> findAll();
    Optional<Student> findByIndex(String index);
    Optional<Student> create(String index,
                             String name,
                             String surname,
                             String email,
                             String password);
    Optional<Student> update(String index,
                             String name,
                             String surname,
                             String email,
                             String password);

    void enlistStudentToCourse(String studentIndex, Long courseId, String professorUsername);

    void deleteByIndex(String index);



}
