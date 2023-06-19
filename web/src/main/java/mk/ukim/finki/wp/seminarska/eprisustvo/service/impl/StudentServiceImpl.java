package mk.ukim.finki.wp.seminarska.eprisustvo.service.impl;

import mk.ukim.finki.wp.seminarska.eprisustvo.model.Course;
import mk.ukim.finki.wp.seminarska.eprisustvo.model.ListensTo;
import mk.ukim.finki.wp.seminarska.eprisustvo.model.Professor;
import mk.ukim.finki.wp.seminarska.eprisustvo.model.Student;
import mk.ukim.finki.wp.seminarska.eprisustvo.model.exceptions.CourseNotFoundException;
import mk.ukim.finki.wp.seminarska.eprisustvo.model.exceptions.InvalidUsernameOrPasswordException;
import mk.ukim.finki.wp.seminarska.eprisustvo.model.exceptions.ProfessorNotFoundException;
import mk.ukim.finki.wp.seminarska.eprisustvo.model.exceptions.StudentNotFoundException;
import mk.ukim.finki.wp.seminarska.eprisustvo.repository.CourseRepository;
import mk.ukim.finki.wp.seminarska.eprisustvo.repository.ProfessorRepository;
import mk.ukim.finki.wp.seminarska.eprisustvo.repository.StudentListensToCourseRepository;
import mk.ukim.finki.wp.seminarska.eprisustvo.repository.StudentRepository;
import mk.ukim.finki.wp.seminarska.eprisustvo.service.StudentService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final CourseRepository courseRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentListensToCourseRepository studentListensToCourseRepository;

    public StudentServiceImpl(StudentRepository studentRepository, ProfessorRepository professorRepository, CourseRepository courseRepository, PasswordEncoder passwordEncoder, StudentListensToCourseRepository studentListensToCourseRepository) {
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
        this.courseRepository = courseRepository;
        this.passwordEncoder = passwordEncoder;
        this.studentListensToCourseRepository = studentListensToCourseRepository;
    }

    @Override
    public List<Student> findAll() {
        return this.studentRepository.findAll();
    }

    @Override
    public Optional<Student> findByIndex(String index) {
        return this.studentRepository.findByIndex(index);
    }

    @Override
    public Optional<Student> create(String index, String name, String surname, String email, String password) {
        if (index==null || index.isEmpty()  || password==null || password.isEmpty())
            throw new InvalidUsernameOrPasswordException();

        Student student = new Student(index, name, surname, email, passwordEncoder.encode(password));
        return Optional.of(this.studentRepository.save(student));
    }

    @Override
    public Optional<Student> update(String index, String name, String surname, String email, String password) {
        Student student = this.studentRepository.findByIndex(index).orElseThrow(() -> new StudentNotFoundException(index));

        student.setName(name);
        student.setSurname(surname);
        student.setEmail(email);

        return Optional.of(this.studentRepository.save(student));
    }

    @Override
    public void enlistStudentToCourse(String studentIndex, Long courseId, String professorUsername) {
        Student student = this.studentRepository.findByIndex(studentIndex)
                .orElseThrow(() -> new StudentNotFoundException(studentIndex));

        Course course = this.courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));

        Professor professor = this.professorRepository.findByUsername(professorUsername)
                .orElseThrow(() -> new ProfessorNotFoundException(professorUsername));

        ListensTo listensTo = new ListensTo(student, course, professor.getUsername());
        this.studentListensToCourseRepository.save(listensTo);
    }

    @Override
    public void deleteByIndex(String index) {
        this.studentRepository.deleteByIndex(index);
    }


    @Override
    public UserDetails loadUserByIndex(String s) throws UsernameNotFoundException {
        return studentRepository.findByIndex(s).orElseThrow(()->new UsernameNotFoundException(s));
    }

}
