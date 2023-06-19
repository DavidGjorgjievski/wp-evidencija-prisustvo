package mk.ukim.finki.wp.seminarska.eprisustvo.service;

import mk.ukim.finki.wp.seminarska.eprisustvo.model.Professor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface ProfessorService {
    List<Professor> findAll();
    Optional<Professor> findByUsername(String username);
    Optional<Professor> create(String username,
                               String name,
                               String surname,
                               String email,
                               String password);
    Optional<Professor> update(String username,
                               String name,
                               String surname,
                               String email);
    void deleteByUsername(String username);

    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;
}
