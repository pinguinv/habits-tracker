package com.example.HabitsTracker.repository;

import com.example.HabitsTracker.models.HabitDone;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;

public interface HabitDoneRepository
        extends PagingAndSortingRepository<HabitDone, Long> {

    List<HabitDone> findByHabit_Id(Long id);

    List<HabitDone> findAllByCreated(LocalDate created);

}
