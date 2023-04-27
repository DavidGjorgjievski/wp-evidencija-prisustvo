package mk.ukim.finki.wp.seminarska.eprisustvo.service.impl;

import mk.ukim.finki.wp.seminarska.eprisustvo.model.Professor;
import mk.ukim.finki.wp.seminarska.eprisustvo.repository.ProfessorRepository;
import mk.ukim.finki.wp.seminarska.eprisustvo.service.ProfessorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorServiceImpl implements ProfessorService {
    private final ProfessorRepository professorRepository;

    public ProfessorServiceImpl(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    @Override
    public List<Professor> findAll() {
        return this.professorRepository.findAll();
    }

    @Override
    public Optional<Professor> findById(String username) {
        return this.professorRepository.findById(username);
    }

    @Override
    public void deleteById(String username) {
        this.professorRepository.deleteById(username);
    }

    @Override
    public Optional<Professor> save(String username, String name, String surname, String email, String password) {
        Professor professor = new Professor(username, name, surname, email, password);
        return Optional.of(this.professorRepository.save(professor));
    }
}
