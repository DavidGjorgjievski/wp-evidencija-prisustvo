package mk.ukim.finki.wp.seminarska.eprisustvo.service;

import mk.ukim.finki.wp.seminarska.eprisustvo.model.Activity;
import mk.ukim.finki.wp.seminarska.eprisustvo.model.Course;
import mk.ukim.finki.wp.seminarska.eprisustvo.model.enumerations.ActivityStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ActivityService {
    List<Activity> findAll();
    Optional<Activity> findById(Long id);
    void deleteById(Long id);
    Optional<Activity> save(String title,
                            String code,
                            LocalDateTime open_date,
                            LocalDateTime close_date,
                            String location,
                            Integer num_attendees,
                            ActivityStatus activityStatus);
}
