package mk.ukim.finki.wp.seminarska.eprisustvo.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.wp.seminarska.eprisustvo.model.Activity;
import mk.ukim.finki.wp.seminarska.eprisustvo.model.Course;
import mk.ukim.finki.wp.seminarska.eprisustvo.model.Professor;
import mk.ukim.finki.wp.seminarska.eprisustvo.model.Student;
import mk.ukim.finki.wp.seminarska.eprisustvo.model.enumerations.ActivityStatus;
import mk.ukim.finki.wp.seminarska.eprisustvo.model.exceptions.*;
import mk.ukim.finki.wp.seminarska.eprisustvo.repository.ActivityRepository;
import mk.ukim.finki.wp.seminarska.eprisustvo.repository.CourseRepository;
import mk.ukim.finki.wp.seminarska.eprisustvo.repository.ProfessorRepository;
import mk.ukim.finki.wp.seminarska.eprisustvo.repository.StudentRepository;
import mk.ukim.finki.wp.seminarska.eprisustvo.service.ActivityService;
import mk.ukim.finki.wp.seminarska.eprisustvo.service.CourseService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepository activityRepository;
    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;
    private final StudentRepository studentRepository;

    private final CourseService courseService;

    private final HttpServletRequest request;

    public ActivityServiceImpl(ActivityRepository activityRepository, CourseRepository courseRepository, ProfessorRepository professorRepository, StudentRepository studentRepository, CourseService courseService, HttpServletRequest request) {
        this.activityRepository = activityRepository;
        this.courseRepository = courseRepository;
        this.professorRepository = professorRepository;
        this.studentRepository = studentRepository;
        this.courseService = courseService;
        this.request = request;
    }

    @Override
    public List<Activity> findAll() {
        return this.activityRepository.findAll();
    }

    @Override
    public Optional<Activity> findById(Long id) {
        return this.activityRepository.findById(id);
    }

    @Override
    public Optional<Activity> create(String title,
                                     LocalDateTime open_date,
                                     LocalDateTime close_date,
                                     String location,
                                     Long courseId,
                                     List<String> professorsUsernames) {
        String code = RandomStringUtils.randomAlphabetic(5);
        Course course = this.courseRepository.findById(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));
        List<Professor> professors = this.professorRepository.findAllById(professorsUsernames);

        Activity activity = new Activity(title, code, open_date, close_date, location, course, professors);

        return Optional.of(this.activityRepository.save(activity));
    }

    @Override
    public Optional<Activity> update(Long id,
                                     String title,
                                     LocalDateTime open_date,
                                     LocalDateTime close_date,
                                     String location,
                                     List<String> professorsUsernames,
                                     Long courseId) {
        Activity activity = this.activityRepository.findById(id).orElseThrow(() -> new ActivityNotFoundException(id));

       Course course = this.courseService.findById(courseId)
               .orElseThrow(() -> new CourseNotFoundException(courseId));

        activity.setTitle(title);
        activity.setOpen_date(open_date);
        activity.setClose_date(close_date);
        activity.setLocation(location);
        List<Professor> professors = this.professorRepository.findAllById(professorsUsernames);
        activity.setProfessors(professors);
        activity.setCourse(course);

        return Optional.of(this.activityRepository.save(activity));
    }

    @Override
    public Optional<Activity> reportStudentAttendanceToActivity(String studentIndex, String activityCode) {
        Student student = this.studentRepository.findByIndex(studentIndex)
                .orElseThrow(() -> new StudentNotFoundException(studentIndex));
        Activity activity = this.activityRepository.findByCode(activityCode)
                .orElseThrow(() -> new InvalidActivityCodeException(activityCode));
//        Activity activity = this.activityRepository.findAll().stream()
//                .filter(a -> a.getActivityStatus().equals(ActivityStatus.CREATED) && a.getCode().equals(activityCode))
//                .findFirst().orElseThrow(() -> new InvalidActivityCodeException(activityCode));

        if ( !activity.getStudents().stream().filter(s -> s.getIndex().equals(student.getIndex())).findFirst().isEmpty() )
            throw new StudentAllreadyInAttendanceToActivityException(studentIndex, activity.getId());
        activity.addAttendee(student);

        return Optional.of(this.activityRepository.save(activity));
    }

    @Override
    public Optional<Activity> removeStudentFromAttendanceToActivity(String studentIndex, Long activityId) {
        Student student = this.studentRepository.findByIndex(studentIndex)
                .orElseThrow(() -> new StudentNotFoundException(studentIndex));
        Activity activity = this.activityRepository.findById(activityId)
                .orElseThrow(() -> new ActivityNotFoundException(activityId));
//        Activity activity = this.activityRepository.findAll().stream()
//                .filter(a -> a.getActivityStatus().equals(ActivityStatus.CREATED) && a.getCode().equals(activityCode))
//                .findFirst().orElseThrow(() -> new InvalidActivityCodeException(activityCode));

        if ( activity.getStudents().stream().filter(s -> s.getIndex().equals(student.getIndex())).findFirst().isEmpty() )
            throw new StudentNotInAttendanceToActivityException(studentIndex, activity.getId());

        activity.removeAttendee(student);

        return Optional.of(this.activityRepository.save(activity));
    }

    @Override
    public Optional<Activity> finishActivity(Long id) {
        Activity activity = this.activityRepository.findById(id)
                .orElseThrow(() -> new ActivityNotFoundException(id));

        activity.setActivityStatus( ActivityStatus.FINISHED );

        return Optional.of(this.activityRepository.save(activity));
    }

    @Override
    public Optional<Activity> cancelActivity(Long id) {
        Activity activity = this.activityRepository.findById(id)
                .orElseThrow(() -> new ActivityNotFoundException(id));

        activity.setActivityStatus( ActivityStatus.CANCELED );

        return Optional.of(this.activityRepository.save(activity));
    }

    @Override
    public void deleteById(Long id) {
        this.activityRepository.deleteById(id);
    }

    @Override
    public void activitiesStatusCheckAndUpdate() {
        List<Activity> activities = this.activityRepository.findAll().stream().filter(activity -> activity.getActivityStatus().equals(ActivityStatus.CREATED)).toList();

        for(Activity activity : activities) {
            if( LocalDateTime.now().isAfter(activity.getClose_date()) ) {
                activity.setActivityStatus( ActivityStatus.FINISHED );
                this.activityRepository.save(activity);
            }
        }
    }

    @Override
    public void saveActivityWithProfessor(Long id, String username) {

    }
}
