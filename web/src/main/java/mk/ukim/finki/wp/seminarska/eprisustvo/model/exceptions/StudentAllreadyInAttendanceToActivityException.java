package mk.ukim.finki.wp.seminarska.eprisustvo.model.exceptions;

public class StudentAllreadyInAttendanceToActivityException extends RuntimeException {
    public StudentAllreadyInAttendanceToActivityException(String studentIndex, Long activityId) {
        super(String.format("Student with index: %s is allready in attendance to activity with id: %d", studentIndex, activityId));
    }
}
