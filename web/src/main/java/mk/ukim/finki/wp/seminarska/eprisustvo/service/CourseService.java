package mk.ukim.finki.wp.seminarska.eprisustvo.service;

import mk.ukim.finki.wp.seminarska.eprisustvo.model.Course;
import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> findAll();
    Optional<Course> findById(Long id);
    Optional<Course> create(String name,
                            String year,
                            String semester,
                            String subjectCode,
                            List<String> professorsUsernames);
    Optional<Course> update(Long id,
                            String name,
                            String year,
                            String semester,
                            List<String> professorsUsernames);
    void deleteById(Long id);
}
