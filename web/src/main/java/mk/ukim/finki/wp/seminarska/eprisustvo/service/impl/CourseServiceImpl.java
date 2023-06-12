package mk.ukim.finki.wp.seminarska.eprisustvo.service.impl;

import mk.ukim.finki.wp.seminarska.eprisustvo.model.Course;
import mk.ukim.finki.wp.seminarska.eprisustvo.model.Professor;
import mk.ukim.finki.wp.seminarska.eprisustvo.model.Subject;
import mk.ukim.finki.wp.seminarska.eprisustvo.model.exceptions.CourseNotFoundException;
import mk.ukim.finki.wp.seminarska.eprisustvo.model.exceptions.SubjectNotFoundException;
import mk.ukim.finki.wp.seminarska.eprisustvo.repository.CourseRepository;
import mk.ukim.finki.wp.seminarska.eprisustvo.repository.ProfessorRepository;
import mk.ukim.finki.wp.seminarska.eprisustvo.repository.SubjectRepository;
import mk.ukim.finki.wp.seminarska.eprisustvo.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final SubjectRepository subjectRepository;
    private final ProfessorRepository professorRepository;

    public CourseServiceImpl(CourseRepository courseRepository, SubjectRepository subjectRepository, ProfessorRepository professorRepository) {
        this.courseRepository = courseRepository;
        this.subjectRepository = subjectRepository;
        this.professorRepository = professorRepository;
    }

    @Override
    public List<Course> findAll() {
        return this.courseRepository.findAll();
    }

    @Override
    public Optional<Course> findById(Long id) {
        return this.courseRepository.findById(id);
    }

    @Override
    public Optional<Course> create(String name,
                                   String year,
                                   String semester,
                                   String subjectCode,
                                   List<String> professorsUsernames) {
        Subject subject = this.subjectRepository.findByCode(subjectCode).orElseThrow(() -> new SubjectNotFoundException(subjectCode));
        List<Professor> professors = this.professorRepository.findAllById(professorsUsernames);
        Course course = new Course(name, year, semester, subject, professors);
        return Optional.of(this.courseRepository.save(course));
    }

    @Override
    public Optional<Course> update(Long id,
                                   String name,
                                   String year,
                                   String semester,
                                   List<String> professorsUsernames) {
        Course course = this.courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));

        course.setName(name);
        course.setYear(year);
        List<Professor> professors = this.professorRepository.findAllById(professorsUsernames);
        course.setProfessors(professors);

        return Optional.of(this.courseRepository.save(course));
    }

    @Override
    public void deleteById(Long id) {
        this.courseRepository.deleteById(id);
    }
}
