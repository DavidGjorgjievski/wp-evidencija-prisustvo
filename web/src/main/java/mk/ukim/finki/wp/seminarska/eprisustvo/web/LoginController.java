package mk.ukim.finki.wp.seminarska.eprisustvo.web;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.wp.seminarska.eprisustvo.service.ProfessorService;
import mk.ukim.finki.wp.seminarska.eprisustvo.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final ProfessorService professorService;

    private final StudentService studentService;

    public LoginController(ProfessorService professorService, StudentService studentService) {
        this.professorService = professorService;
        this.studentService = studentService;
    }

    @GetMapping
    public String getLoginPage(Model model) {
        return "login";
    }

    @PostMapping
    public String login(HttpServletRequest request, Model model) {
        return "redirect:/activities";
    }
}