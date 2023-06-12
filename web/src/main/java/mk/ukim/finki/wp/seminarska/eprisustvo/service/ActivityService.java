package mk.ukim.finki.wp.seminarska.eprisustvo.service;

import mk.ukim.finki.wp.seminarska.eprisustvo.model.Activity;
import mk.ukim.finki.wp.seminarska.eprisustvo.model.enumerations.ActivityStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ActivityService {
    List<Activity> findAll();
    Optional<Activity> findById(Long id);
    Optional<Activity> create(String title,
                              LocalDateTime open_date,
                              LocalDateTime close_date,
                              String location,
                              Long courseId,
                              List<String> professorsUsernames);

    Optional<Activity> update(Long id,
                              String title,
                              LocalDateTime open_date,
                              LocalDateTime close_date,
                              String location,
                              List<String> professorsUsernames,
                              ActivityStatus activityStatus);

    Optional<Activity> reportStudentAttendanceToActivity(String studentIndex, String activityCode);

    Optional<Activity> removeStudentFromAttendanceToActivity(String studentIndex, Long activityId);

    Optional<Activity> finishActivity(Long id);

    Optional<Activity> cancelActivity(Long id);

    void deleteById(Long id);

    void activitiesStatusCheckAndUpdate();
}
