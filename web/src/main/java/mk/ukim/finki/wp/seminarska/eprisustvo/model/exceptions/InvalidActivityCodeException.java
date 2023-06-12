package mk.ukim.finki.wp.seminarska.eprisustvo.model.exceptions;

public class InvalidActivityCodeException extends RuntimeException {
    public InvalidActivityCodeException(String code) {
        super(String.format("The activity code %s is not valid", code));
    }
}
