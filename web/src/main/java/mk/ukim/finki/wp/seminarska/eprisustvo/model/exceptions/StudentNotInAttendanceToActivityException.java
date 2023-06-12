package mk.ukim.finki.wp.seminarska.eprisustvo.model.exceptions;

public class StudentNotInAttendanceToActivityException extends RuntimeException {
    public StudentNotInAttendanceToActivityException(String studentIndex, Long activityId) {
        super(String.format("Student with index: %s is not in attendance to activity with id: %d", studentIndex, activityId));
    }
}
