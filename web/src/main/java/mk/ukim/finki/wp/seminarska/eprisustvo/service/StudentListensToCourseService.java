package mk.ukim.finki.wp.seminarska.eprisustvo.service;

import mk.ukim.finki.wp.seminarska.eprisustvo.model.ListensTo;

import java.util.List;

public interface StudentListensToCourseService {
    List<ListensTo> findAll();
}
