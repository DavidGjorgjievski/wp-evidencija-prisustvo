package mk.ukim.finki.wp.seminarska.eprisustvo.repository;

import mk.ukim.finki.wp.seminarska.eprisustvo.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, String> {
    Optional<Subject> findByCode(String code);
    void deleteByCode(String code);
}
