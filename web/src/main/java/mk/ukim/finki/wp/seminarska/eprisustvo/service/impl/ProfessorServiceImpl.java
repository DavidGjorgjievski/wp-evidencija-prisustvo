package mk.ukim.finki.wp.seminarska.eprisustvo.service.impl;

import mk.ukim.finki.wp.seminarska.eprisustvo.model.Professor;
import mk.ukim.finki.wp.seminarska.eprisustvo.model.exceptions.InvalidUsernameOrPasswordException;
import mk.ukim.finki.wp.seminarska.eprisustvo.model.exceptions.ProfessorNotFoundException;
import mk.ukim.finki.wp.seminarska.eprisustvo.repository.ProfessorRepository;
import mk.ukim.finki.wp.seminarska.eprisustvo.service.ProfessorService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorServiceImpl implements ProfessorService {
    private final ProfessorRepository professorRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfessorServiceImpl(ProfessorRepository professorRepository, PasswordEncoder passwordEncoder) {
        this.professorRepository = professorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Professor> findAll() {
        return this.professorRepository.findAll();
    }

    @Override
    public Optional<Professor> findByUsername(String username) {
        return this.professorRepository.findByUsername(username);
    }

    @Override
    public Optional<Professor> create(String username, String name, String surname, String email, String password) {
        if (username==null || username.isEmpty()  || password==null || password.isEmpty())
            throw new InvalidUsernameOrPasswordException();

        Professor professor = new Professor(username, name, surname, email, passwordEncoder.encode(password));
        return Optional.of(this.professorRepository.save(professor));
    }

    @Override
    public Optional<Professor> update(String username, String name, String surname, String email) {
        Professor professor = this.professorRepository.findByUsername(username).orElseThrow(() -> new ProfessorNotFoundException(username));

        professor.setName(name);
        professor.setSurname(surname);
        professor.setEmail(email);

        return Optional.of(this.professorRepository.save(professor));
    }

    @Override
    public void deleteByUsername(String username) {
        this.professorRepository.deleteByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return professorRepository.findByUsername(s).orElseThrow(()->new UsernameNotFoundException(s));
    }
}
