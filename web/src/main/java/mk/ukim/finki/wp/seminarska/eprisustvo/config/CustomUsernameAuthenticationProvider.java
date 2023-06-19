package mk.ukim.finki.wp.seminarska.eprisustvo.config;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.wp.seminarska.eprisustvo.service.ProfessorService;
import mk.ukim.finki.wp.seminarska.eprisustvo.service.StudentService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomUsernameAuthenticationProvider implements AuthenticationProvider {

    private final StudentService studentService;
    private final ProfessorService professorService;
    private final PasswordEncoder passwordEncoder;
    private final HttpServletRequest request;

    public CustomUsernameAuthenticationProvider(StudentService studentService, ProfessorService professorService,
                                                PasswordEncoder passwordEncoder, HttpServletRequest request) {
        this.studentService = studentService;
        this.professorService = professorService;
        this.passwordEncoder = passwordEncoder;
        this.request = request;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();

        if ("".equals(username)) {
            throw new BadCredentialsException("Invalid Credentials");
        }

        UserDetails userDetails;

        if (studentService.findByIndex(username).isPresent()) {
            userDetails = this.studentService.loadUserByIndex(username);
        } else if (professorService.findByUsername(username).isPresent()) {
            userDetails = this.professorService.loadUserByUsername(username);
        } else {
            throw new BadCredentialsException("Invalid Credentials");
        }

        request.getSession().setAttribute("user", userDetails);

        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
