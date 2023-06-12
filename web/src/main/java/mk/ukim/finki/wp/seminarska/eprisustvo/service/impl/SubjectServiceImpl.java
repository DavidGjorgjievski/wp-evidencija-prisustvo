package mk.ukim.finki.wp.seminarska.eprisustvo.service.impl;

import mk.ukim.finki.wp.seminarska.eprisustvo.model.Subject;
import mk.ukim.finki.wp.seminarska.eprisustvo.repository.SubjectRepository;
import mk.ukim.finki.wp.seminarska.eprisustvo.service.SubjectService;
import org.springframework.stereotype.Service;
import mk.ukim.finki.wp.seminarska.eprisustvo.model.exceptions.SubjectNotFoundException;
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
    public Optional<Subject> findByCode(String code) {
        return this.subjectRepository.findByCode(code);
    }

    @Override
    public Optional<Subject> create(String code, String name, Integer num_credits, Integer semester) {
        Subject subject = new Subject(code, name, num_credits, semester);
        return Optional.of(this.subjectRepository.save(subject));
    }

    @Override
    public Optional<Subject> update(String code, String name, Integer num_credits, Integer semester) {
        Subject subject = this.subjectRepository.findByCode(code).orElseThrow(() -> new SubjectNotFoundException(code));

        subject.setName(name);
        subject.setNum_credits(num_credits);
        subject.setSemester(semester);

        return Optional.of(this.subjectRepository.save(subject));
    }

    @Override
    public void deleteByCode(String code) {
        this.subjectRepository.deleteByCode(code);
    }
}
