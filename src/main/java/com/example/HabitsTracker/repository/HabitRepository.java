package com.example.HabitsTracker.repository;

import com.example.HabitsTracker.models.Habit;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HabitRepository
        extends PagingAndSortingRepository<Habit, Long> {

    // czy to tu jest w ogóle potrzebne?
    boolean existsByHabitName(String habitName);

}
