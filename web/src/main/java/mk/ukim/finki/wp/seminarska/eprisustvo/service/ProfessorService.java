package mk.ukim.finki.wp.seminarska.eprisustvo.service;

import mk.ukim.finki.wp.seminarska.eprisustvo.model.Professor;
import mk.ukim.finki.wp.seminarska.eprisustvo.model.Subject;

import java.util.List;
import java.util.Optional;

public interface ProfessorService {
    List<Professor> findAll();
    Optional<Professor> findById(String username);
    void deleteById(String username);
    Optional<Professor> save(String username, String name, String surname, String email, String password);
}
