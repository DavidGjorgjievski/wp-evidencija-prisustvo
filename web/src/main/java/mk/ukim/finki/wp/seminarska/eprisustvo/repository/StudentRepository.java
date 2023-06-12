package mk.ukim.finki.wp.seminarska.eprisustvo.repository;

import mk.ukim.finki.wp.seminarska.eprisustvo.model.Professor;
import mk.ukim.finki.wp.seminarska.eprisustvo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    Optional<Student> findByIndex(String index);
    void deleteByIndex(String index);

}
