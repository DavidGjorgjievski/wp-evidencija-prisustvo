package mk.ukim.finki.wp.seminarska.eprisustvo.service;

import mk.ukim.finki.wp.seminarska.eprisustvo.model.Course;
import mk.ukim.finki.wp.seminarska.eprisustvo.model.Subject;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> findAll();
    Optional<Course> findById(Long id);
    void deleteById(Long id);
    Optional<Course> save(String name, String year, String semester);
}
