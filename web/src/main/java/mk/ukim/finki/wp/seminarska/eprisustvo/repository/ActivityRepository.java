package mk.ukim.finki.wp.seminarska.eprisustvo.repository;

import mk.ukim.finki.wp.seminarska.eprisustvo.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Optional<Activity> findByCode(String code);
}
