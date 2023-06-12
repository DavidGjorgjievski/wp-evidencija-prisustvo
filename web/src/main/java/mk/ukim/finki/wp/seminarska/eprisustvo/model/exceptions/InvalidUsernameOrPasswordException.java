package mk.ukim.finki.wp.seminarska.eprisustvo.model.exceptions;

public class InvalidUsernameOrPasswordException extends RuntimeException {
    public InvalidUsernameOrPasswordException() {
        super(String.format("The username or password is not valid"));
    }
}
