package mk.ukim.finki.wp.seminarska.eprisustvo;

import mk.ukim.finki.wp.seminarska.eprisustvo.model.*;
import mk.ukim.finki.wp.seminarska.eprisustvo.model.enumerations.ActivityStatus;
import mk.ukim.finki.wp.seminarska.eprisustvo.repository.StudentListensToCourseRepository;
import mk.ukim.finki.wp.seminarska.eprisustvo.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@SpringBootTest
class EPrisustvoApplicationTests {

    @Autowired
    SubjectService subjectService;
    @Autowired
    ProfessorService professorService;
    @Autowired
    StudentService studentService;
    @Autowired
    CourseService courseService;
    @Autowired
    ActivityService activityService;
    @Autowired
    StudentListensToCourseRepository studentListensToCourseRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void testCreateAndListSubjects() {
        this.subjectService.create("F18L1W005", "Biznis i menadzment", 6, 1);
        this.subjectService.create("F18L1W007", "Profesionalni vestini", 6, 1);
        this.subjectService.create("F18L1W020", "Strukturno Programiranje", 6, 1);

        List<Subject> subjects = this.subjectService.findAll();
        assertEquals(subjects.get(0).getCode(), "F18L1W005");
    }

    @Test
    void testUpdateSubject() {
        Subject subject = this.subjectService.findByCode("CSES412").get();
        this.subjectService
                .update("CSES412", "Shabloni za dizajn na korisnicki interfejsi", 6, 5);
        assertEquals(this.subjectService.findByCode("CSES412").get().getName(),
                "Shabloni za dizajn na korisnicki interfejsi");
    }

    @Test
    void testCreateAndListProfessors() {
        this.professorService.create("sasho.gramatikov", "Sasho", "Gramatikov", "sasho.gramatikov@finki.ukim.mk", "123");
        this.professorService.create("gjorgi.mandzarov", "Gjorgi", "Manzdarov", "gjorgji.madjarov@finki.ukim.mk", "456");
        this.professorService.create("sonja.gievska", "Sonja", "Gievska", "sonja.gievska@finki.ukim.mk", "789");
        this.professorService.create("petre.lameski", "Petre", "Lameski", "petre.lameski@finki.ukim.mk", "012");
        this.professorService.create("ivan.kitanovski", "Ivan", "Kitanovski", "ivan.kitanovski@finki.ukim.mk", "345");
        this.professorService.create("milos.jovanovik", "Milos", "Jovanovik", "milos.jovanovik@finki.ukim.mk", "678");
        this.professorService.create("panche.ribarski", "Panche", "Ribarski", "pance.ribarski@finki.ukim.mk", "901");
        this.professorService.create("sonja.filiposka", "Sonja", "Filiposka", "sonja.filiposka@finki.ukim.mk", "234");

        List<Professor> professors = this.professorService.findAll();
        assertEquals(professors.get(0).getUsername(), "sasho.gramatikov");
    }

    @Test
    void testCreateAndListStudents() {
        this.studentService.create("203099", "Dimitar", "Jovanovski", "dimitar.jovanovski@students.finki.ukim.mk", "123");
        this.studentService.create("193018", "Mihail", "Dojdevski", "mihail.dojdevski@students.finki.ukim.mk", "456");
        this.studentService.create("203045", "Anastasija", "Petrova", "anastasija.petrova@students.finki.ukim.mk", "789");
        this.studentService.create("181023", "Elena", "Misheva", "elena.misheva@students.finki.ukim.mk", "012");
        this.studentService.create("171801", "Petar", "Trajkovski", "petar.trajkovski@students.finki.ukim.mk", "345");

        List<Student> students = this.studentService.findAll();
        assertEquals(students.get(0).getIndex(), "203099");
    }

    @Test
    void testCreateAndListCourses() {
        this.courseService.create("BiM", "2022-2023", "winter", "F18L1W005", List.of("gjorgi.mandzarov", "ivan.kitanovski"));
        this.courseService.create("PV", "2021-2022", "winter", "F18L1W007", List.of("ivan.kitanovski"));
        this.courseService.create("SP", "2020-2021", "winter", "F18L1W020", List.of("sasho.gramatikov", "milos.jovanovik", "petre.lameski", "sonja.gievska"));

        List<Course> courses = this.courseService.findAll();
        assertEquals(courses.get(0).getName(), "BiM");
        assertEquals(courses.get(0).getSemester(), "winter");
        assertEquals(courses.get(0).getProfessors().get(0).getUsername(), "gjorgi.mandzarov");
    }

    @Test
    void testCreateAndListActivities() {
        this.activityService.create(
                "Gostinsko predavanje za efikasen IT menadzment",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                "Baraka 1",
                45L,
                List.of("gjorgi.mandzarov", "ivan.kitanovski")
        );

        this.activityService.create(
                "Koristenje na terminal za pogolema produktivnost",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(3),
                "Baraka 2.1",
                46L,
                List.of("ivan.kitanovski")
        );

        List<Activity> activities = this.activityService.findAll();
        assertEquals(activities.get(0).getTitle(), "Gostinsko predavanje za efikasen IT menadzment");
        assertEquals(activities.get(0).getLocation(), "Baraka 1");
        assertEquals(activities.get(0).getProfessors().get(0).getUsername(), "gjorgi.mandzarov");
    }

    @Test
    void testUpdateActivity() {
        this.activityService.update(
                5L,
                "Efikasen IT menadzment",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                "Amfiteatar MF",
                List.of("gjorgi.mandzarov", "ivan.kitanovski"),
                ActivityStatus.CREATED
        );

        Activity activity = this.activityService.findById(5L).get();
        assertEquals(activity.getTitle(), "Efikasen IT menadzment");
        assertEquals(activity.getLocation(), "Amfiteatar MF");
    }

    @Test
    void testReportStudentAttendanceToActivity() {
        this.activityService.reportStudentAttendanceToActivity("181023", "AEACY");
        Activity activity = this.activityService.findById(6L).get();

        assertEquals(activity.getNum_attendees().intValue(), 2);
        assertEquals(activity.getStudents().get(1).getIndex(), "181023");
    }

    @Test
    void testRemoveStudentFromAttendanceToActivity() {
        this.activityService.removeStudentFromAttendanceToActivity("203045", 6L);
        Activity activity = this.activityService.findById(6L).get();

        assertEquals(activity.getNum_attendees().intValue(), 0);
    }

    @Test
    void testEnlistStudentToCourse() {
        this.studentService.enlistStudentToCourse("203045", 46L, "ivan.kitanovski");
        List<ListensTo> enlistings = this.studentListensToCourseRepository.findAll();

        assertEquals(enlistings.get(0).getStudent().getIndex(), "203045");
        assertEquals(enlistings.get(0).getCourse().getName(), "PV");
    }

    @Test
    void testActivitiesStatusCheckAndUpdate() {
        this.activityService.activitiesStatusCheckAndUpdate();
        List<Activity> activities = this.activityService.findAll();
        assertEquals(activities.get(0).getTitle(), "Efikasen IT menadzment");
    }
}
