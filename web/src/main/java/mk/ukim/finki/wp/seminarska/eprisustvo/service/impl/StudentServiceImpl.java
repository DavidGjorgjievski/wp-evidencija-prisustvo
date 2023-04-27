package mk.ukim.finki.wp.seminarska.eprisustvo.service.impl;

import mk.ukim.finki.wp.seminarska.eprisustvo.model.Student;
import mk.ukim.finki.wp.seminarska.eprisustvo.repository.StudentRepository;
import mk.ukim.finki.wp.seminarska.eprisustvo.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> findAll() {
        return this.studentRepository.findAll();
    }

    @Override
    public Optional<Student> findById(String index) {
        return this.studentRepository.findById(index);
    }

    @Override
    public void deleteById(String index) {
        this.studentRepository.deleteById(index);
    }

    @Override
    public Optional<Student> save(String index, String name, String surname, String email, String password) {
        Student student = new Student(index, name, surname, email, password);
        return Optional.of(this.studentRepository.save(student));
    }
}
