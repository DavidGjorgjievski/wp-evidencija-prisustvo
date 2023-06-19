package mk.ukim.finki.wp.seminarska.eprisustvo.service.impl;

import mk.ukim.finki.wp.seminarska.eprisustvo.model.ListensTo;
import mk.ukim.finki.wp.seminarska.eprisustvo.repository.StudentListensToCourseRepository;
import mk.ukim.finki.wp.seminarska.eprisustvo.service.StudentListensToCourseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentListensToCourseServiceImpl implements StudentListensToCourseService {

    private final StudentListensToCourseRepository studentListensToCourseRepository;

    public StudentListensToCourseServiceImpl(StudentListensToCourseRepository studentListensToCourseRepository) {
        this.studentListensToCourseRepository = studentListensToCourseRepository;
    }

    @Override
    public List<ListensTo> findAll() {
        return studentListensToCourseRepository.findAll();
    }
}
