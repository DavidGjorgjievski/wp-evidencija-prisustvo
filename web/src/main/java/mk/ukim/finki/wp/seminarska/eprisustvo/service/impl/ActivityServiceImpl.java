package mk.ukim.finki.wp.seminarska.eprisustvo.service.impl;

import mk.ukim.finki.wp.seminarska.eprisustvo.model.Activity;
import mk.ukim.finki.wp.seminarska.eprisustvo.model.enumerations.ActivityStatus;
import mk.ukim.finki.wp.seminarska.eprisustvo.repository.ActivityRepository;
import mk.ukim.finki.wp.seminarska.eprisustvo.service.ActivityService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepository activityRepository;

    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public List<Activity> findAll() {
        return this.activityRepository.findAll();
    }

    @Override
    public Optional<Activity> findById(Long id) {
        return this.activityRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        this.activityRepository.deleteById(id);
    }

    @Override
    public Optional<Activity> save(String title,
                                   String code,
                                   LocalDateTime open_date,
                                   LocalDateTime close_date,
                                   String location,
                                   Integer num_attendees,
                                   ActivityStatus activityStatus) {
        Activity activity = new Activity(title, code, open_date, close_date, location, num_attendees, activityStatus);
        return Optional.of(activity);
    }
}
