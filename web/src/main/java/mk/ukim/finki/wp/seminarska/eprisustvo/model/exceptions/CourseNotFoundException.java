package mk.ukim.finki.wp.seminarska.eprisustvo.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(Long id) {
        super(String.format("Course with id: %d was not found", id));
    }
}
