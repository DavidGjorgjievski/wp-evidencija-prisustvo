package mk.ukim.finki.wp.seminarska.eprisustvo.service.impl;

import mk.ukim.finki.wp.seminarska.eprisustvo.model.Subject;
import mk.ukim.finki.wp.seminarska.eprisustvo.repository.SubjectRepository;
import mk.ukim.finki.wp.seminarska.eprisustvo.service.SubjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<Subject> findAll() {
        return this.subjectRepository.findAll();
    }

    @Override
    public Optional<Subject> findById(Long id) {
        return this.subjectRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        this.deleteById(id);
    }

    @Override
    public Optional<Subject> save(String name, Integer num_credits, String semester) {
        Subject subject = new Subject(name, num_credits, semester);
        return Optional.of(this.subjectRepository.save(subject));
    }
}
