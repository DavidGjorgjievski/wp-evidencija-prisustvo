package mk.ukim.finki.wp.seminarska.eprisustvo.service;

import mk.ukim.finki.wp.seminarska.eprisustvo.model.Subject;
import java.util.List;
import java.util.Optional;

public interface SubjectService {
    List<Subject> findAll();
    Optional<Subject> findByCode(String code);
    Optional<Subject> create(String code,
                             String name,
                             Integer num_credits,
                             Integer semester);
    Optional<Subject> update(String code,
                             String name,
                             Integer num_credits,
                             Integer semester);
    void deleteByCode(String code);
}
