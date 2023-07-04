package mk.ukim.finki.wp.seminarska.eprisustvo.web;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.wp.seminarska.eprisustvo.model.*;
import mk.ukim.finki.wp.seminarska.eprisustvo.model.exceptions.ActivityNotFoundException;
import mk.ukim.finki.wp.seminarska.eprisustvo.model.exceptions.StudentNotFoundException;
import mk.ukim.finki.wp.seminarska.eprisustvo.service.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/activities/all")
public class AllActivityController {

    private final ActivityService activityService;

    private final CourseService courseService;

    private final ProfessorService professorService;

    private final StudentService studentService;

    private final StudentListensToCourseService studentListensToCourseService;

    public AllActivityController(ActivityService activityService, CourseService courseService, ProfessorService professorService, StudentService studentService, StudentListensToCourseService studentListensToCourseService) {
        this.activityService = activityService;
        this.courseService = courseService;
        this.professorService = professorService;
        this.studentService = studentService;
        this.studentListensToCourseService = studentListensToCourseService;
    }


    @GetMapping
    public String showAllActivities(Model model, HttpServletRequest request) {


        UserDetails userDetails = (UserDetails) request.getSession().getAttribute("user");

        String username = userDetails.getUsername();

        Boolean adminRole = true;


        if (studentService.findByIndex(username).isPresent()) {
            adminRole = false;
        } else if (professorService.findByUsername(username).isPresent()) {
            adminRole = true;
        } else {
            adminRole = false;
        }

        this.activityService.activitiesStatusCheckAndUpdate();

        List<Activity> activities = activityService.findAll();

        List<Activity> customActivities = new ArrayList<>();
        if(adminRole){
            for (Activity activity : activities) {
                List<String> professorUsernames = activity.getProfessors().stream()
                        .map(Professor::getUsername)
                        .collect(Collectors.toList());
                if (professorUsernames.contains(username)) {
                    customActivities.add(activity);
                }
            }
        }else{
            List<ListensTo> listensToList = this.studentListensToCourseService.findAll();
            for (Activity activity : activities) {
                boolean matchFound = false;

                for (ListensTo listensTo : listensToList) {
                    if (activity.getCourse().equals(listensTo.getCourse()) &&
                            listensTo.getStudent().getIndex().equals(username)) {
                        matchFound = true;
                        break;
                    }
                }

                if (matchFound) {
                    customActivities.add(activity);
                }
            }
        }
        // sorting the activities by open_date so the latest ones come on top
        Comparator<Activity> dateComparator = Comparator.comparing(Activity::getOpen_date).reversed();
        Collections.sort(customActivities, dateComparator);

        model.addAttribute("activities", customActivities);
        model.addAttribute("adminRole", adminRole);
        return "all-activity";
    }

    @GetMapping("/view-attendants/{id}")
    public String viewAttendants(@PathVariable Long id, Model model){

        Activity activity = this.activityService.findById(id).orElseThrow(()-> new ActivityNotFoundException(id));
        List<Student> students = activity.getStudents();
        model.addAttribute("activity",activity);
        model.addAttribute("students",students);
        return "all-activity-attendants";
    }

    @GetMapping("/view-attendants/{id}/remove-attendant/{studentId}")
    public String viewAttendantsAfterOneRemoved(@PathVariable Long id, @PathVariable String studentId, Model model) {
        Activity activity = this.activityService.findById(id).orElseThrow(() -> new ActivityNotFoundException(id));
        Student student = this.studentService.findByIndex(studentId).orElseThrow(() -> new StudentNotFoundException(studentId));
        this.activityService.removeStudentFromAttendanceToActivity(student.getIndex(), id);
        List<Student> students = activity.getStudents();
        model.addAttribute("activity", activity); // Include the 'activity' object with the correct ID
        model.addAttribute("students", students);
        return "redirect:/activities/all/view-attendants/{id}";
    }

    @GetMapping("/finish-activity/{id}")
    public String finishActivity(@PathVariable Long id, Model model){
        this.activityService.finishActivity(id);
        return "redirect:/activities/all";
    }

    @GetMapping("/cancel-activity/{id}")
    public String cancelActivity(@PathVariable Long id, Model model){
        this.activityService.cancelActivity(id);
        return "redirect:/activities/all";
    }

    @GetMapping("/edit-form/{id}")
    public String editActivity(@PathVariable Long id, Model model) {
        if (this.activityService.findById(id).isPresent()) {
            Activity activity = this.activityService.findById(id).get();
            List<Course> courses = this.courseService.findAll();
            List<Professor> professors = this.professorService.findAll();
            model.addAttribute("activity",activity);
            model.addAttribute("courses",courses);
            model.addAttribute("professors",professors);
            return "all-add-activity";
        }
        return "redirect:/activity?error=ActivityNotFound";
    }

    @PostMapping("/add")
    public String saveActivity(
            @RequestParam(required = false) Long id,
            @RequestParam String name,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime openDateTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime closedDateTime,
            @RequestParam String location,
            @RequestParam Long course,
            @RequestParam List<String> professors
    ) {

        if (id != null) {
            this.activityService.update(id, name, openDateTime, closedDateTime, location,professors,course);
        } else {
            this.activityService.create(name,openDateTime,closedDateTime,location,course,professors);
        }
        return "redirect:/activities/all";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteActivity(@PathVariable Long id){
        this.activityService.deleteById(id);
        return "redirect:/activities/all";
    }

}
