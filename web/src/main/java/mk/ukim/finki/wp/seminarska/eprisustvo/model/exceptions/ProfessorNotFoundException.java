package mk.ukim.finki.wp.seminarska.eprisustvo.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProfessorNotFoundException extends RuntimeException {
    public ProfessorNotFoundException(String username) {
        super(String.format("Professor %s was not found", username));
    }
}
