package mk.ukim.finki.wp.seminarska.eprisustvo.repository;

import mk.ukim.finki.wp.seminarska.eprisustvo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
}
